package com.example.wallet.controller;

import com.example.wallet.model.*;
import com.example.wallet.repository.TransactionRepository;
import com.example.wallet.repository.RedisWalletRepository;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.exception.TransactionBadRequest;
import com.example.wallet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;

@RestController
public class TransactionResource {

	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private RedisWalletRepository redisWalletRepository;
	@Autowired
	private UserService userService;

	private static final String TOPIC = "payments";
	private static final Logger logger = LoggerFactory.getLogger(TransactionResource.class);

	@PostMapping("/sendMoney")
	@ResponseStatus(HttpStatus.CREATED)
	Transaction addBal(@RequestBody Transaction transaction) throws Exception {
		transaction.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		User sender = userService.getAUser(String.valueOf(transaction.getSid()));
		User receiver = userService.getAUser(String.valueOf(transaction.getRid()));
		if(sender==null || receiver==null){
			logger.info("No wallet for sender or receiver");
			throw new TransactionBadRequest();
		}

		RedisWallet redisWalletSender = redisWalletRepository.getWallet(sender.getId());
		RedisWallet redisWalletReceiver = redisWalletRepository.getWallet(receiver.getId());
		Wallet senderWallet = walletRepository.findByUserId(sender.getId());
		Wallet receiverWallet = walletRepository.findByUserId(receiver.getId());

		int amt = transaction.getAmount();

		if (senderWallet.getBalance() < amt) {
			throw new Exception("Not Sufficient Balance");
		}
		senderWallet.setBalance(senderWallet.getBalance()-amt);
		redisWalletSender.setAmount(redisWalletSender.getAmount()-amt);

		receiverWallet.setBalance(receiverWallet.getBalance()+amt);
		redisWalletReceiver.setAmount(redisWalletReceiver.getAmount()+amt);

		transaction.setStatus("SUCCESS");
		logger.info(String.format("$$ -> Producing Transaction --> %s", transaction));
		walletRepository.save(receiverWallet);
		walletRepository.save(senderWallet);
		redisWalletRepository.updateWallet(redisWalletReceiver);
		redisWalletRepository.updateWallet(redisWalletSender);
		Transaction save = transactionRepository.save(transaction);
		kafkaTemplate.send(TOPIC, Integer.toString(save.getId()));
		return save;
	}


	@GetMapping("/getBal/{id}")
	int getBal(@PathVariable int id) throws Exception {
		RedisWallet redisWallet = null;
		redisWallet = redisWalletRepository.getWallet(id);
		// If not present in the Redis then go for MySQL
		if(redisWallet==null){
			Wallet wallet = walletRepository.findByUserId(id);

			if(wallet==null) throw new Exception("Wallet Not Found");
			else {
				return wallet.getBalance();
			}

		}else{
			return redisWallet.getAmount();
		}
	}
	
	@PostMapping("/addBalance")
	BalanceDetails addBalance(@RequestBody BalanceDetails request){

		logger.info(request.toString());
		Wallet wallet = walletRepository.findByUserId(request.getUserId());

		wallet.setBalance(request.getAmount()+wallet.getBalance());

		RedisWallet redisWallet = redisWalletRepository.getWallet(request.getUserId());
		redisWallet.setAmount(request.getAmount()+wallet.getBalance());

		walletRepository.save(wallet);
		redisWalletRepository.updateWallet(redisWallet);
		return request;
	}

	@GetMapping("/txnHistory/{id}")
	String getTransactionHistory(@PathVariable int id) {
		logger.info(String.format("$$ -> Producing Transaction --> %s", id));
		String new_id = Integer.toString(id)+"txn";
		kafkaTemplate.send(TOPIC, new_id);
		return "You will get the file on your email";
	}
}

package com.example.wallet.controller;

import com.example.wallet.model.*;
import com.example.wallet.repository.RedisWalletRepository;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.exception.WalletNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class WalletResource {
	
    @Autowired
    private WalletRepository walletRepository;
    
    @Autowired
    private RedisWalletRepository redisWalletRepository;

    private static final Logger logger = LoggerFactory.getLogger(WalletResource.class);

    @GetMapping("/findAllWallet")
    List<Wallet> findAllWallet() {
        return walletRepository.findAll();
    }

    @GetMapping("/wallet/{id}")
    Wallet findOneWallet(@PathVariable int id) {
        logger.info("/wallet/{id} called with id "+ id);
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
    }

    @PostMapping("/createNewWallet")
    @ResponseStatus(HttpStatus.CREATED)
    Wallet CreateNewWallet(@RequestBody Wallet newWallet) {
        
        Wallet wallet = walletRepository.save(newWallet);
        //Saving balance in redis
        RedisWallet redisWallet = new RedisWallet();
        redisWallet.setAmount(newWallet.getBalance());
        redisWallet.setUserId(newWallet.getUserId());
        redisWalletRepository.updateWallet(redisWallet);
        return wallet;
    }
    
    @PutMapping("/updateWallet")
    Wallet updateWallet(@RequestBody Wallet newWallet) {
        Wallet wallet = walletRepository.save(newWallet);
        
        RedisWallet redisWallet = redisWalletRepository.getWallet(newWallet.getUserId());
        if(redisWallet==null){
        	redisWallet = new RedisWallet();
        	redisWallet.setUserId(newWallet.getUserId());
        }
        redisWallet.setAmount(newWallet.getBalance());
        redisWalletRepository.updateWallet(redisWallet);
        return wallet;
    }
}

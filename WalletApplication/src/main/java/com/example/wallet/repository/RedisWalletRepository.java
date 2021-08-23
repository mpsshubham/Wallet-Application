package com.example.wallet.repository;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.example.wallet.model.RedisWallet;
import com.example.wallet.model.Wallet;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class RedisWalletRepository {
	
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    WalletRepository walletRepository;
    private static final String KEY = "wallet";
    public Boolean updateWallet(RedisWallet wallet) {
        try {
            Map userHash = new ObjectMapper().convertValue(wallet, Map.class);
            redisTemplate.opsForHash().put(KEY, Integer.toString(wallet.getUserId()), userHash);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public RedisWallet getWallet(Integer uid) {

        String id = Integer.toString(uid);
        Map WalletMap = (Map) redisTemplate.opsForHash().get(KEY, id);
        RedisWallet redisWallet;

        if(WalletMap==null || WalletMap.equals(null) || WalletMap.isEmpty())
        {
            redisWallet = new RedisWallet();
            Wallet wallet = walletRepository.findByUserId(uid);

            redisWallet.setAmount(wallet.getBalance());
            redisWallet.setUserId(wallet.getUserId());
            Map walletHash = new ObjectMapper().convertValue(redisWallet, Map.class);
            redisTemplate.opsForHash().put(KEY, Integer.toString(redisWallet.getUserId()), walletHash);
        }
        else {

        	redisWallet = new ObjectMapper().convertValue(WalletMap, RedisWallet.class);
        }
        return redisWallet;
    }

}

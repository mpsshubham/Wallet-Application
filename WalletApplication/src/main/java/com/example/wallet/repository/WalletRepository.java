package com.example.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.wallet.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer>{
	
    //@Query("SELECT w FROM Wallet w WHERE w.user_id = ?1")
	Wallet findByUserId(int userId);

}

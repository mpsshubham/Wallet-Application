package com.example.wallet.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("RedisWallet")
public class RedisWallet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private int userId;
	private int amount;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public RedisWallet() {
		
	}
	
	public RedisWallet(int userId, int amount) {
		this.userId = userId;
		this.amount = amount;
	}

}

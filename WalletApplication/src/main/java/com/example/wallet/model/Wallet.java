package com.example.wallet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Wallet {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int userId;
	private int balance;
	private boolean isActive;
	private String walletType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
	
	public Wallet(int id, int userId, int balance, boolean isActive, String walletType) {
		this.id = id;
		this.userId = userId;
		this.balance = balance;
		this.isActive = isActive;
		this.walletType = walletType;
	}
	
	public Wallet() {
	}
	
}

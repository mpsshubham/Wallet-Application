package com.example.wallet.exception;

public class WalletNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WalletNotFoundException(int id) {

        super("Wallet id not found : " + id);
    }

}

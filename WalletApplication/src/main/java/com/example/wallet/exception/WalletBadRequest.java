package com.example.wallet.exception;

public class WalletBadRequest extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public WalletBadRequest() {

        super("WalletBadRequest " );
    }
}

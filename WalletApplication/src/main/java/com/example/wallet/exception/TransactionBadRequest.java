package com.example.wallet.exception;

public class TransactionBadRequest extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TransactionBadRequest(){
        super("TransactionBadRequest");
    }
}

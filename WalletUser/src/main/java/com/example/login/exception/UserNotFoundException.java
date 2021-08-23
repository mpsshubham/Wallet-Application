package com.example.login.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public UserNotFoundException(int id) {
        super("User id not found : " + id);
    }

}

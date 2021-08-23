package com.example.wallet.model;

public class User {
	
	private int id;
	private String username;
	private String password;
	private boolean isActive;
	String auth;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public User() {
		
	}
	
	public User(int id, String username, String password, boolean isActive, String authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
		this.auth = authorities;
	}
	
	

}

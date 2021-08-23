package com.example.login.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private boolean isActive;
	String auth;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorityList=new ArrayList<>();
		String[] allauthorities = this.auth.split(":");
		for(String authority:allauthorities) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
			authorityList.add(grantedAuthority);
		}
		return authorityList;
	}

	public String getAuth() {
		return auth;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isActive;
	}

	public User(String username, String password, boolean isActive, String authorities) {
		this.username = username;
		this.password = password;
		this.isActive = isActive;
		this.auth = authorities;
	}

	public int getId() {
		return id;
	}

	public User() {
	}

}

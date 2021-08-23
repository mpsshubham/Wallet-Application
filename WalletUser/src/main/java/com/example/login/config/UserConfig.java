package com.example.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.login.service.MyUserDetailsService;

@EnableWebSecurity
public class UserConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	MyUserDetailsService service;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers("/transaction**").hasAuthority("admin")
		.antMatchers("/wallet/**").hasAnyAuthority("writer","admin")
		.antMatchers("/**").permitAll()
		.and()
		.formLogin();

	}



}

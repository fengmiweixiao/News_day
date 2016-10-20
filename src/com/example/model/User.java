package com.example.model;

public class User {
	private String Username;
	private String Userpwd;
	private String token;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getUserpwd() {
		return Userpwd;
	}
	public void setUserpwd(String userpwd) {
		Userpwd = userpwd;
	}

}

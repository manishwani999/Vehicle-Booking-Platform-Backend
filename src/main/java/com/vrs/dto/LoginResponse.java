package com.vrs.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String role;
    private Long id;
    private String email;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "LoginResponse [token=" + token + ", role=" + role + ", id=" + id + ", email=" + email + "]";
	}
    
    
}

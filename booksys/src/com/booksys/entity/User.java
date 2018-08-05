package com.booksys.entity;

public class User {
	
	private Integer id;
	private String userName;
	private String passWord;
	private String email;
	private Integer isLock;
	private String rePassWord;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getIsLock() {
		return isLock;
	}
	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}
	public String getrePassWord() {
		return rePassWord;
	}
	public void setrePassWord(String rePassWord) {
		this.rePassWord = rePassWord;
	}
}

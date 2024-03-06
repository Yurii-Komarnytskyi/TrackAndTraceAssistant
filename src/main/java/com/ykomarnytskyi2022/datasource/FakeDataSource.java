package com.ykomarnytskyi2022.datasource;

public class FakeDataSource {

	private String username;
	private String password;
	private String JDBC_URL;
	
	public FakeDataSource(String username, String password, String jDBC_URL) {
		this.username = username;
		this.password = password;
		JDBC_URL = jDBC_URL;
		
		System.out.println("************************************************************************");
		System.out.println(this.toString());
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getJDBC_URL() {
		return JDBC_URL;
	}
	
	@Override
	public String toString() {
		return "FakeDataSource [username=" + username + ", password=" + password + ", JDBC_URL=" + JDBC_URL + "]";
	}	
	
}

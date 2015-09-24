package com.iderly.control;

public class LoginManager {
	private static LoginManager instance = new LoginManager();
	
	public static LoginManager getInstance () {
		return instance;
	}
}

package com.iderly.control;

import android.util.Log;

public class LoginManager {
	private static LoginManager instance = new LoginManager();
	public static final String postUrl = "http://iderly.kenrick95.org/caregiver/login";
	
	public static LoginManager getInstance () {
		return instance;
	}
	
	public void doLogin(String email, String password, HttpPostRequestListener listener) {
		new HttpPostRequest(postUrl, listener) {

			@Override
			public void onFinish(int statusCode, String responseText) {
				((HttpPostRequestListener) this.mixed[0]).onFinish(statusCode, responseText);
			}
			
		}.addParameter("email", email)
			.addParameter("password", password)
			.send();
	}
}

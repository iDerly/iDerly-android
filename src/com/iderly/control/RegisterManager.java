package com.iderly.control;

public class RegisterManager {
	private static RegisterManager instance = new RegisterManager();
	
	public static final String postUrl = "http://iderly.kenrick95.org/caregiver/register";
	
	public static RegisterManager getInstance () {
		return instance;
	}
	
	public void doRegister(String email, String password, String name, String user_id, HttpPostRequestListener listener) {
		new HttpPostRequest(postUrl, listener) {

			@Override
			public void onFinish(int statusCode, String responseText) {
				((HttpPostRequestListener) this.mixed[0]).onFinish(statusCode, responseText);
			}
			
		}.addParameter("email", email)
			.addParameter("password", password)
			.addParameter("name", name)
			.addParameter("user_id", user_id)
			.send();
	}
}

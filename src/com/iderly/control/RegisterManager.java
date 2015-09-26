package com.iderly.control;

public class RegisterManager {
	private static RegisterManager instance = new RegisterManager();
	
	public static final String registerPOSTUrl = "http://iderly.kenrick95.org/caregiver/register";
	public static final String forgotPasswordPOSTUrl = "http://iderly.kenrick95.org/";
	
	public static RegisterManager getInstance () {
		return instance;
	}
	
	public void doRegister(String email, String password, String name, String user_id, HttpPostRequestListener listener) {
		new HttpPostRequest(registerPOSTUrl, listener) {

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
	
	public void forgotPassword(String email, HttpPostRequestListener listener) {
		new HttpPostRequest(forgotPasswordPOSTUrl, listener) {
			@Override
			public void onFinish(int statusCode, String responseText) {
				((HttpPostRequestListener) this.mixed[0]).onFinish(statusCode, responseText);
			}
		}.addParameter("email", email)
			.send();
	}
}

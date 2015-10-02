package com.iderly.control;

public class RegisterManager {
	private static RegisterManager instance = new RegisterManager();
	
	public static final String registerPOSTUrl = "http://iderly.kenrick95.org/caregiver/register";
	public static final String forgotPasswordPOSTUrl = "http://iderly.kenrick95.org/caregiver/forgot";
	
	public static RegisterManager getInstance () {
		return instance;
	}
	
	public void doRegister(String email, String password, String name, HttpPostRequestListener listener) {
		new HttpPostRequest(registerPOSTUrl, listener) {

			@Override
			public void onFinish(int statusCode, String responseText) {
				((HttpPostRequestListener) this.mixed[0]).onFinish(statusCode, responseText);
			}
			
		}.addParameter("email", email)
			.addParameter("password", password)
			.addParameter("name", name)
			.addParameter("device_id", Global.deviceId)
			.send();
	}
	
	public void forgotPassword(String email, HttpPostRequestListener listener) {
		new HttpPostRequest(forgotPasswordPOSTUrl, listener) {
			@Override
			public void onFinish(int statusCode, String responseText) {
				((HttpPostRequestListener) this.mixed[0]).onFinish(statusCode, responseText);
			}
		}.addParameter("email", email)
			.addParameter("device_id", Global.deviceId)
			.send();
	}
}

package com.iderly.control;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LoginManager {
	private static LoginManager instance = new LoginManager();
	public static final String loginPOSTURL = "http://iderly.kenrick95.org/caregiver/login";
	public static final String logoutPOSTURL = "http://iderly.kenrick95.org/caregiver/logout";
	
	public static LoginManager getInstance () {
		return instance;
	}
	
	public void doLogin(final String email, final String password, HttpPostRequestListener listener) {
		new HttpPostRequest(loginPOSTURL, listener) {

			@Override
			public void onFinish(int statusCode, String responseText) {
				if(statusCode == HttpURLConnection.HTTP_OK) {
					try {
						JSONObject response = new JSONObject(responseText);
						
						SessionController.remove("session_id", "email", "password");
						if(response.getInt("status") == 0) {
							SessionController.set("session_id", response.getJSONArray("message").getString(0));
							SessionController.set("email", email);
							SessionController.set("password", password);
						}
					} catch (JSONException e) {
						// Kenrick or the Internet's fault
					}
				}
				
				((HttpPostRequestListener) this.mixed[0]).onFinish(statusCode, responseText);
			}
			
		}.addParameter("email", email)
			.addParameter("password", password)
			.send();
	}
	
	
	public void doLogout(HttpPostRequestListener listener) {
		new HttpPostRequest(logoutPOSTURL, listener) {

			@Override
			public void onFinish(int statusCode, String responseText) {
				Log.d("logout donk", "response: " + responseText);
				if(statusCode == HttpURLConnection.HTTP_OK) {
					try {
						JSONObject response = new JSONObject(responseText);
						
						if(response.getInt("status") == 0) {
							Log.d("logout donk", "sukses");
							SessionController.remove("session_id");
						}
					} catch (JSONException e) {
						// Kenrick or the Internet's fault
					}
				}
				
				((HttpPostRequestListener) this.mixed[0]).onFinish(statusCode, responseText);
			}
			
		}.send();
	}
}

package com.iderly.boundary;

import java.net.HttpURLConnection;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.Global;
import com.iderly.control.HttpPostRequest;
import com.iderly.control.HttpPostRequestListener;
import com.iderly.control.SessionController;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginCaregiverActivity extends Activity {
	private LinearLayout loginMessagesPlaceholder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_caregiver);
		
		// Setup Login Messages
		this.loginMessagesPlaceholder = (LinearLayout) findViewById(R.id.login_messages);
		
		if(SessionController.contains("session_id")) {
			Log.d("cek session", "logged in before, sir!");
			// New intent to HomeActivity?
			Intent intent = new Intent(this, CaregiverHomeActivity.class);
			this.startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_caregiver, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Login caregiver here
	public void loginCaregiver(View view) {
//		int s = 1;
//		if (s == 1) {
//			Intent t = new Intent(this, CaregiverHomeActivity.class);
//			startActivity(t);
//			return;
//		}
		
		this.clearMessages();
		
		EditText emailField = (EditText) findViewById(R.id.email_field);
		EditText passwordField = (EditText) findViewById(R.id.password_field);
		
		String email = emailField.getText().toString();
		String password = passwordField.getText().toString();
		
		int valid = 1;
		if (email == null || email.isEmpty()) {
			this.putMessage("Email address is empty!");
			valid = 0;
		}
		
		if (password == null || password.isEmpty()) {
			this.putMessage("Password is empty!");
			valid = 0;
		}
		
		if(valid == 1) {
			ProgressDialog pd = ProgressDialog.show(this, null, "Logging in...", true);
			Global.getLoginManager().doLogin(email, password, new HttpPostRequestListener (pd) {
				@Override
				public void onFinish(int statusCode, String responseText) {
					((ProgressDialog) this.mixed[0]).dismiss();
					
					Log.d("login caregiver", "response: " + responseText);
					if(statusCode == HttpURLConnection.HTTP_OK) {
						if(SessionController.contains("session_id")) {
							Intent intent = new Intent(LoginCaregiverActivity.this, CaregiverHomeActivity.class);
							LoginCaregiverActivity.this.finish();
							LoginCaregiverActivity.this.startActivity(intent);
						}
					}
				}
			});
		}
	}
	
	// Start Forgot Password Activity
	public void forgotPasswordCaregiver (View view) {
//		Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
//		startActivity(forgotPasswordIntent);
		
		// RETRIEVE DEVICE ID, HTTP REQUEST HERE!!
	}
	
	// Start Register Caregiver Activity
	public void registerCaregiver (View view) {
		Intent registerCaregiverIntent = new Intent(this, RegisterCaregiverActivity.class);
		startActivity(registerCaregiverIntent);
	}
	
	/**
	 * Clears all messages in {@link #loginMessages}
	 */
	private void clearMessages () {
		this.loginMessagesPlaceholder.removeAllViews();
	}
	
	/**
     * Adds a message in {@link #loginMessages}
     * @param message
     */
	private void putMessage (String message) {
		TextView t = new TextView(this);
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		t.setText("\u2022 " + message);
        this.loginMessagesPlaceholder.addView(t);
	}
}

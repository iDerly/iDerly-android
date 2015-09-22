package com.iderly.boundary;

import java.net.HttpURLConnection;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.HttpPostRequest;

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
	public static final String authenticationUrl = "http://iderly.kenrick95.org/caregiver/login";
	
	private LinearLayout loginMessagesPlaceholder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_caregiver);
		
		// Setup Login Messages
		this.loginMessagesPlaceholder = (LinearLayout) findViewById(R.id.login_messages);
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
			new HttpPostRequest(authenticationUrl, pd) {

				@Override
				public void onFinish(int statusCode, String responseText) {
					((ProgressDialog) this.mixed[0]).dismiss();
					Log.d("login caregiver", "status code: " + statusCode);
					Log.d("login caregiver", "response: " + responseText);
				}
				
			}.addParameter("email", email)
				.addParameter("password", password)
				.send();
		}
	}
	
	// Start Forgot Password Actvity
	public void forgotPasswordCaregiver(View view) {
		Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
		startActivity(forgotPasswordIntent);
	}
	
	/**
	 * Clears all messages in {@link #loginMessages}
	 */
	private void clearMessages() {
		this.loginMessagesPlaceholder.removeAllViews();
	}
	
	/**
     * Adds a message in {@link #loginMessages}
     * @param message
     */
	private void putMessage(String message) {
		TextView t = new TextView(this);
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		t.setText("\u2022 " + message);
        this.loginMessagesPlaceholder.addView(t);
	}
}

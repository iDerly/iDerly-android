package com.iderly.boundary;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForgotPasswordActivity extends Activity {
	private LinearLayout forgotPasswordMessages;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		
		// Setup ForgotPasswordMessages
		this.forgotPasswordMessages = (LinearLayout) findViewById(R.id.ForgotPassword_Messages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
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
	
	/**
	 * Handle request submission for forget password
	 * @param v		the View firing the event
	 */
	public void forgetPassword (View view) {
		this.clearMessages();
		view.setEnabled(false);
		
		// Call HTTP request here to ask for PHP
		String email = ((EditText) findViewById(R.id.emailAddress_field)).getText().toString();
		
		int valid = 1;
		if (email == null || email.isEmpty()) {
			this.putMessage("Email address is empty!");
			valid = 0;
		}
		
		switch (valid) {
			case 0:
				view.setEnabled(true);
				break;
				
			default:
				break;
		}
	}
	
	/**
     * Clear all messages held in {@link #registerMessages}
     */
    private void clearMessages() {
        this.forgotPasswordMessages.removeAllViews();
    }
    
    /**
     * Add a message to {@link #registerMessages}
     * @param message	the message to be added into {@link #registerMessages}
     */
    private void putMessage(String message) {
        TextView textView = new TextView(this);
        textView.setText("\u2022 " + message);
        textView.setLayoutParams(
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.forgotPasswordMessages.addView(textView);
    }
}

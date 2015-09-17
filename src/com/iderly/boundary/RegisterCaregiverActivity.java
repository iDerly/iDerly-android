package com.iderly.boundary;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.RegisterManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegisterCaregiverActivity extends Activity {
	private LinearLayout registerMessages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_caregiver);
		
		// Setup Register Messages
		this.registerMessages = (LinearLayout) findViewById(R.id.register_messages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_caregiver, menu);
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
	
	public void registerCaregiver (View view) {
		this.clearMessages();
		view.setEnabled(false);
		
		String name = ((EditText) findViewById(R.id.name_field)).getText().toString();
		String email = ((EditText) findViewById(R.id.email_field)).getText().toString();
		String password = ((EditText) findViewById(R.id.password_field)).getText().toString();
		String confirmPassword = ((EditText) findViewById(R.id.password_confirm_field)).getText().toString();
		
		int valid = 1;
		
		if (name == null || name.isEmpty()) {
			this.putMessage("Name is empty!");
			valid = 0;
		}
		
		if (email == null || email.isEmpty()) {
			this.putMessage("Email address is empty!");
			valid = 0;
		}
		
		if (password == null || password.isEmpty()) {
			this.putMessage("Password is empty!");
			valid = 0;
		}
		
		if (confirmPassword == null || confirmPassword.isEmpty()) {
			this.putMessage("Confirm password is empty!");
			valid = 0;
		}
		
		if (password != null && !password.isEmpty() && password.length() < RegisterManager.PASSWORD_MINIMUM_LENGTH) {
			this.putMessage("Password is too short!");
			valid = 0;
		}
		
		if (password != null && !password.isEmpty() && confirmPassword != null && !confirmPassword.isEmpty() && !password.equals(confirmPassword)) {
			this.putMessage("Password and confirm password mismatch!");
			valid = 0;
		}
		
		switch (valid) {
			case 0:
				view.setEnabled(true);
				break;
				
			default:
				// CALL HTTP REQUEST HERE!
				break;
		}
	}
	
	/**
     * Clear all messages held in {@link #registerMessages}
     */
    private void clearMessages() {
        this.registerMessages.removeAllViews();
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
        this.registerMessages.addView(textView);
    }
}

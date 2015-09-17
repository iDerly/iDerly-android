package com.iderly.boundary;

import com.iderly.boundary.*;
import com.example.iderly.R;
import com.iderly.control.GameManager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class TitleActivity extends Activity {
	private int gameMode = GameManager.CLASSIC_MODE;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
		
		// Register ToggleButton listener for Game Mode
		ToggleButton gameModeToggle = (ToggleButton) findViewById(R.id.game_mode_btn);
		gameModeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					TitleActivity.this.gameMode = GameManager.UNLIMITED_MODE;
				} else {
					TitleActivity.this.gameMode = GameManager.CLASSIC_MODE;
				}
			}
		});
		
		// Setting up Action Bar
		this.actionBar = this.getActionBar();
		this.actionBar.setSubtitle("Welcome!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.title_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		// Open Caregiver Login activity
		if (id == R.id.ActionBar_LoginCaregiver) {
			Intent loginCaregiverIntent = new Intent(this, LoginCaregiverActivity.class);
            startActivity(loginCaregiverIntent);
            
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * An event listener to start the Face-Name matching game
	 * @param view	the View object firing the click event
	 */
	public void startGameActivity (View view) {
		// Start intent to the next page
		return;
	}
}

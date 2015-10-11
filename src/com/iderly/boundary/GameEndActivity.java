package com.iderly.boundary;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.example.iderly.R;
import com.iderly.control.GameManager;
import com.iderly.control.Global;

public class GameEndActivity extends Activity { 
	private ActionBar actionBar;
	private RelativeLayout gameEndLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_game_end);
		gameEndLayout = (RelativeLayout)findViewById(R.id.gameEndLayout);
		
		// Setting up Action Bar
		this.actionBar = this.getActionBar();

		//listener for "Click anywhere to continue" 
        gameEndLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent gameIntent = new Intent(GameEndActivity.this, TitleActivity.class);
			    startActivity(gameIntent);
			    GameEndActivity.this.finish();
			}
        });
		 
	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.title_activity_menu, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		
//		// Open Caregiver Login activity
//		if (id == R.id.ActionBar_LoginCaregiver) {
//			Intent loginCaregiverIntent = new Intent(this, LoginCaregiverActivity.class);
//            startActivity(loginCaregiverIntent);
//            
//			return true;
//		}
//		
//		return super.onOptionsItemSelected(item);
//	}
	
}

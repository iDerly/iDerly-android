package com.iderly.boundary;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.iderly.R;
import com.iderly.control.GameManager;
import com.iderly.control.Global;

public class GameRoundActivity extends Activity { 
	private ActionBar actionBar;
	private ArrayList<Button> button;
	private TextView headerText, pictureText, instText;
	private RelativeLayout gameRoundLayout;
	private boolean waitForContinue;
	private Color bgColor, buttonColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_game_round);
		gameRoundLayout = (RelativeLayout)findViewById(R.id.gameRoundLayout);
		
		// Setting up Action Bar
		this.actionBar = this.getActionBar();
		this.actionBar.setSubtitle("Welcome!");
		
		button = new ArrayList<Button>();
		button.add((android.widget.Button)findViewById(R.id.gameChoice1));
		button.add((android.widget.Button)findViewById(R.id.gameChoice2));
		button.add((android.widget.Button)findViewById(R.id.gameChoice3));
		button.add((android.widget.Button)findViewById(R.id.gameChoice4));
		
		headerText = (android.widget.TextView)findViewById(R.id.gameRoundHeaderText);
		pictureText = (android.widget.TextView)findViewById(R.id.gameRoundSubText);
		instText = (android.widget.TextView)findViewById(R.id.gameRoundFooterText);
		 
//		Global.getGameManager().StartGame();
		ResetRound();
		getData();
	} 
	
	/**
	 * An event listener to start the Face-Name matching game
	 * @param view	the View object firing the click event
	 */
	public void gameListener (View view) {
		
		if (waitForContinue) {
			NextRound();
			return;
		}
		
		//Find out which button is firing
		int buttonNo = -1;
		if (view.getId() == R.id.gameChoice1) buttonNo = 0;
		else if (view.getId() == R.id.gameChoice2) buttonNo = 1;
		else if (view.getId() == R.id.gameChoice3) buttonNo = 2;
		else if (view.getId() == R.id.gameChoice4) buttonNo = 3; 
		
		if (buttonNo>=0){
			//set screen and buttons to correct/false based on asnwer result 
			//Global.getGameManager().SendUserInput(buttonNo); 
			if (buttonNo == 3){ //correct
				//gameRoundLayout.setBackgroundColor(Color.argb(255, 200, 255, 200));
				headerText.setText(R.string.GameRound_PictureCorrectText);
			} else {		   //incorrect
				//gameRoundLayout.setBackgroundColor(Color.argb(255, 255, 200, 200));
				headerText.setText(R.string.GameRound_PictureWrongText); 
			}
			
			for(int i=0;i<4;++i){ 
				//button.get(i).getBackground().setColorFilter(0xFFFFAAAA, PorterDuff.Mode.MULTIPLY); //red  
				//button.get(i).setEnabled(false);
			}
			//button.get(3).getBackground().setColorFilter(0xFFBBFFBB, PorterDuff.Mode.MULTIPLY); //green
 
			//set pictureText to photo remark
			//pictureText.setText("\"" + remark + "\"");

			//"Click anywhere to continue" 
			instText.setText(R.string.GameRound_ContinueText);
			waitForContinue = true; 
			
            gameRoundLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) { 
					NextRound();
					return;
				}
            });
		}
		return;
	}

	private int cnt = 0;
	public boolean getData(){
//		while(Global.getGameManager().GetNextRound()){
//			//updatePhoto
//			for(int i=0;i<4;++i){
//				button.get(i).setText(Global.getGameManager().getChoice(i));
//			}
//		}
		++cnt;
		return (cnt<5);
	}
	private void NextRound(){
		gameRoundLayout.setOnClickListener(null); 
		if (getData()){  
			ResetRound();
		} else {
			Log.d("ending","return"); 
			GoToEnd();
		}
	}
	
	public void ResetRound(){ 
		gameRoundLayout.setBackgroundColor(Color.argb(255, 255, 255, 255));
		for(int i=0;i<4;++i){
//			button.get(i).setBackgroundResource(android.R.drawable.btn_default);
//			button.get(i).getBackground().clearColorFilter();
//			button.get(i).setText("Candinegara"); 
//			button.get(i).setEnabled(true);
		}
		
		headerText.setText(R.string.GameRound_PictureText);
		pictureText.setText("");
		instText.setText(""); 
		
		waitForContinue = false;
	} 
	
	private void GoToEnd(){
		Intent gameEndIntent = new Intent(this, GameEndActivity.class);
	    startActivity(gameEndIntent);
	}

	
}

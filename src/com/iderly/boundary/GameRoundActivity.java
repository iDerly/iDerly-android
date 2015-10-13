package com.iderly.boundary;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	private ImageView photoImage;
	private RelativeLayout gameRoundLayout;
	private boolean waitForContinue;
	private Color bgColor, buttonColor;

	private LinearLayout progBarHolder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_game_round);
		gameRoundLayout = (RelativeLayout)findViewById(R.id.gameRoundLayout);
		progBarHolder = (LinearLayout)findViewById(R.id.game_round_progbar);
		photoImage = (ImageView)findViewById(R.id.gamePhoto);
		
		// Setting up Action Bar
		this.actionBar = this.getActionBar();
		this.actionBar.setSubtitle("Welcome!");
		
		button = new ArrayList<Button>();
		button.add((Button)findViewById(R.id.gameChoice1));
		button.add((Button)findViewById(R.id.gameChoice2));
		button.add((Button)findViewById(R.id.gameChoice3));
		button.add((Button)findViewById(R.id.gameChoice4));
		
		headerText =  (TextView)findViewById(R.id.gameRoundHeaderText);
		pictureText = (TextView)findViewById(R.id.gameRoundSubText);
		instText = 	  (TextView)findViewById(R.id.gameRoundFooterText);
		  
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
		
		Log.d("button text",button.get(0).getText() + " "+ button.get(0).getTextSize()); 
		Log.d("button text",((Button)findViewById(R.id.gameChoice1)).getText() + " "+ ((Button)findViewById(R.id.gameChoice1)).getTextSize()); 
		
		if (buttonNo>=0){
			//set screen and buttons to correct/false based on asnwer result 
			boolean roundResult = Global.getGameManager().SendUserInput(buttonNo); 
			if (roundResult){//buttonNo == 3){ //correct
				gameRoundLayout.setBackgroundColor(Color.argb(255, 200, 255, 200));
				headerText.setText(R.string.GameRound_PictureCorrectText);
	            UpdateView(true);
			} else {		   //incorrect
				((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(100);
				gameRoundLayout.setBackgroundColor(Color.argb(255, 255, 200, 200));
				headerText.setText(R.string.GameRound_PictureWrongText); 
	            UpdateView(false);
			}
			
			for(int i=0;i<4;++i){ 
				button.get(i).setEnabled(false);
				if (Global.getGameManager().GetAnswerResult(i))
					button.get(i).getBackground().setColorFilter(0xFFBBFFBB, PorterDuff.Mode.MULTIPLY); //green
				else 
					button.get(i).getBackground().setColorFilter(0xFFFFAAAA, PorterDuff.Mode.MULTIPLY); //red
			}
			
			
//			//set pictureText to photo remark
			pictureText.setText("\"" + Global.getGameManager().getPhoto().getRemarks() + "\"");

//			//"Click anywhere to continue" 
			instText.setText(R.string.GameRound_ContinueText);
			waitForContinue = true; 
//			
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
		
		// Retrieves next photo from the algo. Returns false if game is over 
		boolean nextRoundAvailable = Global.getGameManager().GetNextRound();
		
		if (nextRoundAvailable){
			//updatePhoto 
			Log.d("photo",GameManager.getPhoto().toString());
			Log.d("photo",GameManager.getPhoto().getImageBase64());
			
			byte[] decodedString = Base64.decode(GameManager.getPhoto().getImageBase64(), Base64.DEFAULT);
			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
			photoImage.setImageBitmap(decodedByte);
			
			for(int i=0;i<4;++i){
				button.get(i).setText(Global.getGameManager().getChoice(i));
			}
		}
		return nextRoundAvailable;
		 
	}
	
	private void UpdateView(boolean inp){  

		DisplayMetrics dm = new DisplayMetrics();
		int width = progBarHolder.getWidth(); 
		
		View progBar = new View(this);
		if (GameManager.getGameMode() == GameManager.CLASSIC_MODE){
			if (inp)
				progBar.setBackgroundResource(R.drawable.barcorrect);
			else 
				progBar.setBackgroundResource(R.drawable.barincorrect); 
			progBar.setLayoutParams(new LinearLayout.LayoutParams(width/10, LayoutParams.MATCH_PARENT));
			progBarHolder.addView(progBar);
		} else {
			if (!inp){
				progBar.setBackgroundResource(R.drawable.barincorrect);
				progBar.setLayoutParams(new LinearLayout.LayoutParams(width/3, LayoutParams.MATCH_PARENT));
				progBarHolder.addView(progBar);
			}
		} 
		
		//progBar.set(color);
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
			button.get(i).setBackgroundResource(android.R.drawable.btn_default);
			button.get(i).getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY); //white  
			button.get(i).setText("Candinegara"); 
			button.get(i).setEnabled(true);

		}
		
		button.get(0).setText("TEST!!");
		Log.d("button text",button.get(0).getText() + " "+ button.get(0).getTextSize()); 
		Log.d("button text",((Button)findViewById(R.id.gameChoice1)).getText() + " "+ ((Button)findViewById(R.id.gameChoice1)).getTextSize()); 
		
		Log.d("gameChoice1",""+R.id.gameChoice1);
		Log.d("gameChoice2",""+R.id.gameChoice2);
		Log.d("gameChoice3",""+R.id.gameChoice3);
		Log.d("gameChoice4",""+R.id.gameChoice4);
		
		Log.d("gameRoundFooterText",""+R.id.gameRoundFooterText);
		Log.d("gameRoundSubText",   ""+R.id.gameRoundSubText);
		Log.d("gameRoundHeaderText",""+R.id.gameRoundHeaderText); 
		
		
		headerText.setText(R.string.GameRound_PictureText);
		pictureText.setText("");
		instText.setText(""); 
		
		waitForContinue = false;
	} 
	
	private void GoToEnd(){
		Intent gameEndIntent = new Intent(this, GameEndActivity.class);
	    startActivity(gameEndIntent);
	    this.finish();
	}

	
}

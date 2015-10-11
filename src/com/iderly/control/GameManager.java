package com.iderly.control;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.RandomAccess;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;

import com.iderly.boundary.EditElderProfileActivity;
import com.iderly.entity.Photo;

public class GameManager {
	public static int CLASSIC_MODE = 0;
	public static int CLASSIC_ROUNDS = 10;
	public static int UNLIMITED_MODE = 1;
	public static int UNLIMITED_LIVES = 3;
 
	private static int GameMode;
    private static Random randomGenerator;
    
    private static Photo currentPhoto;
    private static ArrayList<String> selectedNames;
    private static String correctName;

	public static ArrayList<Photo> photoList;
	
	private static GameManager instance = new GameManager();

	public static GameManager getInstance(){
		return instance;
	}
	
	public static void StartGame(){
		Initialize();
	}  
	
	private static void Initialize(){
		//reset randomgen
		long seed = System.nanoTime();
		randomGenerator = new Random(seed);
		 
		RetrievePhotoDatabase(); 
		
		if (GameMode == CLASSIC_MODE)
			GameModeClassic.Initialize();
		else 
			GameModeUnlimited.Initialize(); 
	}                                                       
	
	public static boolean GetNextRound(){
		//updates currentPhoto and nameList. returns false if game is over
		
		int photoID;
		if (GameMode == CLASSIC_MODE)
			photoID = GameModeClassic.GetNextIndex();
		else 
			photoID = GameModeUnlimited.GetNextIndex(); 
		
		if (photoID<0) return false;
		currentPhoto  = photoList.get(photoID);
		
		//generate random names 
		selectedNames = new ArrayList<String>();
		correctName = currentPhoto.getName();
		selectedNames.add(correctName);
		
		while(selectedNames.size()<4){ 
			int nextget = randomGenerator.nextInt(photoList.size());
			String RandName = photoList.get(nextget).getName();
			boolean unique = true;
			for(int i=0; i<selectedNames.size(); ++i){
				if (RandName == selectedNames.get(i)) {
					unique=false;
					break;
				}
			}
			if (unique) selectedNames.add(RandName);
		}
		Collections.shuffle(selectedNames);
		 
		return true;
	}
	
	public static boolean SendUserInput(int choice){
		//this function receives user input from Activity 
		//updates score and database accordingly based on currentPhoto
		//returns result
		boolean roundResult = (selectedNames.get(choice) == correctName);	
		if (GameMode == CLASSIC_MODE)
			GameModeClassic.ReturnResult(roundResult);
		else 
			GameModeUnlimited.ReturnResult(roundResult); 
		
		UpdateDatabase(roundResult?1:0);
		
		return roundResult;
	}

	public static void UpdateDatabase(int inp){
		//update database for currentPhoto
		//0: shown, user incorrect answer
		//1: shown, user correct answer.
		String postUrl = "http://iderly.kenrick95.org/game/inc_photo_stats";
		
		new HttpPostRequest(postUrl) {
			@Override
			public void onFinish(int statusCode, String responseText) { 
				//what to put here what what
				//assume peter's fault
			}
		}.addParameter("photo_id", ""+currentPhoto.getId())
			.addParameter("option", ""+inp) 
			.send();
	}
	
	public static Photo getPhoto(){
		return currentPhoto;
	}
	
	public static String getChoice(int ind){
		return selectedNames.get(ind);
	} 

	public static int getGameMode(){
		return GameMode;
	}

	public static void setGameMode(int inp){
		GameMode = inp;
	}
	
	private static void RetrievePhotoDatabase(){
		//get list of photos somehow
	}
	
}

package com.iderly.control;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.RandomAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;

import com.iderly.boundary.EditElderProfileActivity;
import com.iderly.boundary.ElderPhotoGalleryList;
import com.iderly.boundary.GameRoundActivity;
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

    private static String startTime,endTime;
    
	public static ArrayList<Photo> photoList;
	
	private static GameManager instance = new GameManager();

	public static GameManager getInstance(){
		return instance;
	}
	
	public static void StartGame(){
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		startTime = s.format(new Date());
		if (GameMode == CLASSIC_MODE)
			GameModeClassic.Initialize();
		else 
			GameModeUnlimited.Initialize(); 
	}  
	
	public static void Initialize(){
		//reset randomgen
		long seed = System.nanoTime();
		randomGenerator = new Random(seed);
		 
		RetrievePhotoDatabase();  
	}                                                       
	
	public static boolean GetNextRound(){
		//updates currentPhoto and nameList. returns false if game is over
		
		int photoID;
		if (GameMode == CLASSIC_MODE)
			photoID = GameModeClassic.GetNextIndex();
		else 
			photoID = GameModeUnlimited.GetNextIndex(); 
		
		if (photoID<0) return false;
		
		Log.d("Photolist size","Size: "+photoList.size()+" id: "+photoID);
		currentPhoto  = photoList.get(photoID);
		Log.d("curphot",currentPhoto.toString());
		
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
	
	public static boolean GetAnswerResult(int choice){
		return (selectedNames.get(choice) == correctName);
	}
	
	public static boolean SendUserInput(int choice){
		//this function receives user input from Activity 
		//updates score and database accordingly based on currentPhoto
		//returns result
		boolean roundResult = GetAnswerResult(choice);
		if (GameMode == CLASSIC_MODE)
			GameModeClassic.ReturnResult(roundResult);
		else 
			GameModeUnlimited.ReturnResult(roundResult); 
		
		UpdateDatabase(roundResult?1:0);
		
		return roundResult;
	}

	public static void UpdateDatabase(int inp){
		//update database for currentPhoto, 0: shown, user incorrect answer, 1: shown, user correct answer.
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

	public static String getScoreText(){ 
		if (GameMode == CLASSIC_MODE)
			return GameModeClassic.GetScore();
		else 
			return GameModeUnlimited.GetScore();
	}
	public static int getScoreInt(){ 
		if (GameMode == CLASSIC_MODE)
			return GameModeClassic.GetScoreInt();
		else 
			return GameModeUnlimited.GetScoreInt();
	}
	
	private static void RetrievePhotoDatabase(){ 
		String postUrl = "http://iderly.kenrick95.org/elder/photos";
		String deviceID = Global.deviceId;
		String targetUrl = postUrl + "/" + deviceID;
		Log.d("photoRet","RETRIEEEEEEEVE " + targetUrl);
		
		photoList = new ArrayList<Photo>();
		
		new HttpPostRequest(targetUrl, photoList) {
			@Override
			public void onFinish(int statusCode, String responseText) {  
				if(statusCode == HttpURLConnection.HTTP_OK) {;
 
					try {
						JSONObject response = new JSONObject(responseText);
						
						ArrayList<Photo> photos = (ArrayList<Photo>) this.mixed[0];
						if(statusCode == HttpURLConnection.HTTP_OK) {
							if(response.getInt("status") == 0) {
								JSONArray messages = response.getJSONArray("message");
								for(int i = 0, size = messages.length(); i < size; ++i) {
									JSONObject message = messages.getJSONObject(i);
									photos.add(new Photo(-1, message.getString("attachment"), message.getString("name"), message.getString("remarks")));
								} 
							} 
						}
					} catch (JSONException e) {
						// As always, either Kenrick or the Internet's fault
					}
				}

				for (int i=0; i<photoList.size(); ++i){
					Log.d("photo",photoList.get(i).toString());
				}
			}
		}.addParameter("device_id", deviceID)
			.send();

		
	}

	
	public static void SendGameResult(){ 
		String postUrl = "http://iderly.kenrick95.org/game/add_result";
		String deviceID = Global.deviceId;  

		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		endTime = s.format(new Date());
		
		photoList = new ArrayList<Photo>();
		
		new HttpPostRequest(postUrl) {
			@Override
			public void onFinish(int statusCode, String responseText) {  
				if(statusCode == HttpURLConnection.HTTP_OK) {
				} 
			}
		}.addParameter("device_id", deviceID)
		.addParameter("score", ""+getScoreInt())
		.addParameter("time_start", startTime)
		.addParameter("time_end", endTime)
		.addParameter("mode", (GameMode==CLASSIC_MODE)?"classic":"unlimited")
			.send();
		
		Log.d("Send result", ""+deviceID+" Score: "+getScoreText()+" starttime "+startTime+" endtime "+endTime);
		
	}
	
}

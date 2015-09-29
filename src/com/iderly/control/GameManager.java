package com.iderly.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.RandomAccess;

import com.iderly.entity.Photo;

public class GameManager {
	public static int CLASSIC_MODE = 0;
	public static int UNLIMITED_MODE = 1;

	public static ArrayList<Photo> photoList;

    private static Random randomGenerator;
	
	public static void StartGame(int GameMode){
		Initialize();
		if (GameMode == CLASSIC_MODE){
			if (GameManager.photoList.size() >= 10)	GameModeClassic.Play();
		} else { 
			GameModeUnlimited.Play();
		}
	} 
	                                                                              
	
	public static boolean PlayRound(int photoID){
		//plays a round using the photo denoted by the photoID. Returns whether or not player is correct  
		Photo curPhoto  = photoList.get(photoID);
		
		//prepare list of names
		ArrayList<String> SelectedNames = new ArrayList<String>();
		String CorrectName = curPhoto.getName();
		SelectedNames.add(CorrectName);
		
		while(SelectedNames.size()<4){ 
			int nextget = randomGenerator.nextInt(photoList.size());
			String RandName = photoList.get(nextget).getName();
			boolean unique = true;
			for(int i=0; i<SelectedNames.size(); ++i){
				if (RandName == SelectedNames.get(i)) {
					unique=false;
					break;
				}
			}
			if (unique) SelectedNames.add(RandName);
		}
		
		//show photo from photoList[photoID] for the game
		//show names from SelectedNames for the game
		
		curPhoto.incDisplayCount();
		
		//if ([user selected name] == correctName){
		//curPhoto.incCorrectCount //update photo correct number
		return true;
		
		//} else 
		//return false;
	}
	
	private static void Initialize(){
		//reset randomgen
		long seed = System.nanoTime();
		randomGenerator = new Random(seed);

		getPhotoDatabase();
	}
	
	private static void getPhotoDatabase(){
		//get list of photos somehow
	}
}

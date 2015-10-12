package com.iderly.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.view.View;

public class GameModeUnlimited {
	
	public static ArrayList<Integer> indexList;

	private static int lives;
	private static int score;
	private static int len, ite;
	
	public static int GetNextIndex(){
		//returns next index of photo in database. -1 if game ends
		if (ite>=len || lives == 0) return -1;
		
		int ret = indexList.get(ite);
		++ite;
		return ret;
	}        
	
	public static void ReturnResult(boolean res){
		if (res) ++score;
		else --lives;
	}

	public static String GetScore(){
		return ""+score;
	}
	
	public static void Initialize(){
		len = GameManager.photoList.size();
		lives = GameManager.UNLIMITED_LIVES;
		score = 0;
		ite = 0;
		
		//initialize random sequence
		indexList = new ArrayList<Integer>();
		for(int i=0; i<GameManager.photoList.size(); ++i) indexList.add(i);
		Collections.shuffle(indexList);
	} 
}

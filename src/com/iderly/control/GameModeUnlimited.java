package com.iderly.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameModeUnlimited {
	
	public static ArrayList<Integer> indexList;
	
	public static void Play(){
		Initialize();
		int lives = 3;
		int score = 0;
		int len = GameManager.photoList.size();
		 
		for(int i=0; i<len; ++i){
			if (GameManager.PlayRound(indexList.get(i))){
				--lives;
			} else {
				++score;
				//increment correct counter 
			}
			if (lives==0) break;
		}
		//display score
	}        
	
	private static void Initialize(){ 
		//initialize random sequence
		indexList = new ArrayList<Integer>();
		for(int i=0; i<GameManager.photoList.size(); ++i) indexList.add(i);
		Collections.shuffle(indexList, new Random(System.nanoTime()));
	}
}

package com.iderly.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.iderly.boundary.GameRoundActivity;

import android.util.Log;
import android.view.View;

public class GameModeClassic {
 
	private static final double BASE_CHANCE = 3;
	private static final double CHANCE_MULTIPLIER = 2;

	private static ArrayList<Double> chanceList;
	private static double totalChance;
	private static boolean[] result;
	private static int rnd, score; 
	
	public static int GetNextIndex(){
		if (rnd >= GameManager.CLASSIC_ROUNDS) return -1;
		
		Random randomGenerator = new Random();
		Double select = randomGenerator.nextDouble()*totalChance;

		Log.d("select",""+select);
		int ret = 0;
		while (select > chanceList.get(ret)){
			++ret;
			select -= chanceList.get(ret);
		} 
		
		totalChance -= chanceList.get(ret);
		chanceList.set(ret,(double)0);
		return ret;
	}

	public static void ReturnResult(boolean res){
		result[rnd] = res;
		if (res) ++score;
		++rnd;
	}

	public static String GetScore(){
		return ""+score+"/"+GameManager.CLASSIC_ROUNDS;
	}
	
	public static int GetScoreInt(){
		return score;
	}
	
	public static void Initialize(){
		InitChanceList();
		result = new boolean[GameManager.CLASSIC_ROUNDS];
		rnd = 0;
		score = 0;
	}
	
	private static void InitChanceList(){
		Log.d("Chancelist","chancelist");
		totalChance = 0.f;
		chanceList = new ArrayList<Double>();
		Log.d("ChanceListSize",""+GameManager.photoList.size()+" "+totalChance);
		for(int i=0; i<GameManager.photoList.size(); ++i){
			double chance = BASE_CHANCE - GameManager.photoList.get(i).getCorrectRatio()*CHANCE_MULTIPLIER;
			totalChance += chance;
			chanceList.add(chance);
		}  
		Log.d("ChancelistRet",""+chanceList.size()+" / "+GameManager.photoList.size()+" total "+totalChance);
	} 
}

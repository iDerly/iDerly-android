package com.iderly.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameModeClassic {

	private static final int NO_OF_ROUNDS = 10;
	private static final double BASE_CHANCE = 3;
	private static final double CHANCE_MULTIPLIER = 2;

	private static ArrayList<Double> chanceList;
	private static double totalChance;
	
	public static void Play(){
		Initialize();
		 
		boolean[] record = new boolean[NO_OF_ROUNDS];
		for(int rnd=0; rnd<NO_OF_ROUNDS; ++rnd){
			
			record[rnd] = GameManager.PlayRound(GetRandomIndex());
		}
		
	}
	
	private static int GetRandomIndex(){
		Random randomGenerator = new Random();
		Double select = randomGenerator.nextDouble()*totalChance;
		
		int ret = 0;
		while (select > chanceList.get(ret)){
			++ret;
			select -= chanceList.get(ret);
		}
		
		totalChance -= chanceList.get(ret);
		chanceList.set(ret,(double)0);
		return ret;
	}
	
	private static void Initialize(){
		InitChanceList();
	}
	
	private static void InitChanceList(){
		totalChance = 0;
		chanceList = new ArrayList<Double>();
		for(int i=0; i<GameManager.photoList.size(); ++i){
			double chance = BASE_CHANCE - GameManager.photoList.get(i).getCorrectRatio()*CHANCE_MULTIPLIER;
			totalChance += chance;
			chanceList.add(chance);
		}  
	}
}

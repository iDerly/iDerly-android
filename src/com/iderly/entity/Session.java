package com.iderly.entity;

import java.util.List;

import com.iderly.control.Global;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Pair;

public class Session {
	private static Session instance = new Session();
	private static Context context;
	
	private static SharedPreferences preferences;
	
	private Session() {
		// Empty constructor
	}
	
	public static void init(Context ctx) {
		context = ctx;
		preferences = context.getSharedPreferences(Global.APP_NAME, 0);
	}
	
	public static void set(String key, String value) {
		preferences.edit().putString(key, value).apply();
	}
	
	public static void set(Pair<String, String>... keyValuePairs) {
		Editor edits = preferences.edit();
		for(Pair<String, String> keyValuePair: keyValuePairs) {
			edits.putString(keyValuePair.first, keyValuePair.second);
		}
		edits.apply();
	}
	
	public static void set(List<Pair<String, String>> keyValuePairs) {
		Editor edits = preferences.edit();
		for(Pair<String, String> keyValuePair: keyValuePairs) {
			edits.putString(keyValuePair.first, keyValuePair.second);
		}
		edits.apply();
	}
	
	public static String get(String key) {
		return preferences.getString(key, null);
	}
	
	public static void remove(String key) {
		preferences.edit().remove(key).apply();
	}
	
	public static void remove(String... keys) {
		Editor edits = preferences.edit();
		for(String key: keys) {
			edits.remove(key);
		}
		edits.apply();
	}
	
	public static void remove(List<String> keys) {
		Editor edits = preferences.edit();
		for(String key: keys) {
			edits.remove(key);
		}
		edits.apply();
	}
	
	public static boolean contains(String key) {
		return preferences.contains(key);
	}
}

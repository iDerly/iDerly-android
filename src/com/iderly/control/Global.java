package com.iderly.control;

import android.content.Context;

public class Global {
	public static String APP_NAME = "iDerly";
	private static UserManager userManager;
	private static LoginManager loginManager;
	private static ImageManager imageManager;
	
	public static void init (Context context) {
		ImageManager.init(context);
		userManager = UserManager.getInstance();
		loginManager = LoginManager.getInstance();
		imageManager = ImageManager.getInstance();
	}
	
	public static UserManager getUserManager () {
		return userManager;
	}
	
	public static LoginManager getLoginManager () {
		return loginManager;
	}
	
	public static ImageManager getImageManager () {
		return imageManager;
	}
}

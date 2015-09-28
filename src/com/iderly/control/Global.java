package com.iderly.control;

import android.content.Context;
import android.provider.Settings.Secure;

public class Global {
	public static String APP_NAME = "iDerly";
	private static UserManager userManager;
	private static LoginManager loginManager;
	private static ImageManager imageManager;
	private static RegisterManager registerManager;
	
	public static String deviceId;
	
	public static void init (Context context) {
		SessionController.init(context);
		ImageManager.init(context);
		userManager = UserManager.getInstance();
		loginManager = LoginManager.getInstance();
		imageManager = ImageManager.getInstance();
		registerManager = RegisterManager.getInstance();
		
		deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
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
	
	public static RegisterManager getRegisterManager() {
		return registerManager;
	}
}

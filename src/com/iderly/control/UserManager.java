package com.iderly.control;

import java.util.ArrayList;

import com.iderly.entity.Caregiver;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

public class UserManager {
	public static String addElderPOSTUrl = "http://iderly.kenrick95.org/caregiver/add_elder";
	
	private static UserManager instance = new UserManager();
	private User user;
	
	private UserManager () {
		this.user = null;
	}
	
	public static UserManager getInstance() {
		return instance;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void removeUser() {
		this.user = null;
	}
	
	// Call this or createCaregiver depending on the user type read from DB
	public User createElder (String email, String userId, String name, Photo profPic, ArrayList<Photo> photosGallery) {
		return this.user = new User(userId, User.ELDER, name, profPic, photosGallery);
	}
	
	// Call this or createElder depending on the user type read from DB
	public Caregiver createCaregiver (String email, String userId, String name, ArrayList<User> elders) {
		Caregiver c = new Caregiver(email, userId, User.CAREGIVER, name, elders);
		this.user = c;
		return c;
	}
	
	public static void registerElder(String elderName, String elderDeviceId
			, String profilePictureString, HttpPostRequestListener listener) {
		new HttpPostRequest(addElderPOSTUrl, listener) {
			@Override
			public void onFinish(int statusCode, String responseText) {
				((HttpPostRequestListener) this.mixed[0]).onFinish(statusCode, responseText);
			}
		}.addParameter("elder_name", elderName)
			.addParameter("elder_device_id", elderDeviceId)
			.addParameter("caregiver_device_id", Global.deviceId)
			.addParameter("elder_attachment", profilePictureString)
			.send();
	}
}

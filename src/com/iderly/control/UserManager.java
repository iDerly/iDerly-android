package com.iderly.control;

import java.util.ArrayList;

import com.iderly.entity.Caregiver;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

public class UserManager {
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
	public User createElder (String email, String userId, String name, int gamesPlayed, double avgScore, Photo profPic, ArrayList<Photo> photosGallery) {
		return this.user = new User(email, userId, User.ELDER, name, gamesPlayed, avgScore, profPic, photosGallery);
	}
	
	// Call this or createElder depending on the user type read from DB
	public Caregiver createCaregiver (String email, String userId, String name, int gamesPlayed, double avgScore, ArrayList<Photo> photosGallery, ArrayList<User> elders) {
		Caregiver c = new Caregiver(email, userId, User.CAREGIVER, name, gamesPlayed, avgScore, photosGallery, elders);
		this.user = c;
		return c;
	}
	
	public void registerUser () {
		
	}
}

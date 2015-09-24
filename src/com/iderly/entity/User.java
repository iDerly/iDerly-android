package com.iderly.entity;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	public static final String ELDER = "elder";
	public static final String CAREGIVER = "caregiver";
	private String email;
	private String userId;
	private String type;
	private String name;
	private int gamesPlayed;
	private double avgScore;
	private Photo profPic;
	private ArrayList<Photo> photosGallery;
	
	public User (String email, String userId, String type, String name, int gamesPlayed, double avgScore, Photo profPic, ArrayList<Photo> photosGallery) {
		this.email = email;
		this.userId = userId;
		this.type = type;
		this.name = name;
		this.avgScore = avgScore;
		this.profPic = profPic;
		this.photosGallery = photosGallery;
	}
	
	public String getEmail () {
		return this.email;
	}
	
	public String getUserId () {
		return this.userId;
	}
	
	public String getType () {
		return this.type;
	}
	
	public String getName () {
		return this.name;
	}
	
	public int getGamesPlayed () {
		return this.gamesPlayed;
	}
	
	public double getAvgScore () {
		return this.avgScore;
	}
	
	public Photo getProfPic () {
		return this.profPic;
	}
	
	public ArrayList<Photo> getPhotosGallery () {
		return this.photosGallery;
	}
	
	public void setEmail (String email) {
		this.email = email;
	}
	
	public void setUserId (String userId) {
		this.userId = userId;
	}
	
	public void setUserType (String type) {
		this.type = type;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void incrementGamesPlayed () {
		this.gamesPlayed++;
	}
	
	public void updateAvgScore (int score) {
		this.avgScore = (this.avgScore * this.gamesPlayed) / (double) score;
	}
	
	public void setProfPic (Photo profPic) {
		this.profPic = profPic;
	}
	
	public void addPhotoGallery (Photo p) {
		this.photosGallery.add(p);
	}
	
	
	
	
	// PARCELABLE IMPLEMENTATION
	public User (Parcel in) {
		this.email = in.readString();
		this.userId = in.readString();
		this.type = in.readString();
		this.name = in.readString();
		this.gamesPlayed = in.readInt();
		this.avgScore = in.readDouble();
		this.profPic = (Photo) in.readParcelable(Photo.class.getClassLoader());
		this.photosGallery = (ArrayList<Photo>) in.readArrayList(Photo.class.getClassLoader());
	}
	
	@Override
	public int describeContents () {
		return 0;
	}
	
	@Override
	public void writeToParcel (Parcel dest, int flags) {
		dest.writeString(this.email);
		dest.writeString(this.userId);
		dest.writeString(this.type);
		dest.writeString(this.name);
		dest.writeInt(this.gamesPlayed);
		dest.writeDouble(this.avgScore);
		dest.writeParcelable(this.profPic, flags);
		dest.writeList(this.photosGallery);
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel (Parcel in) {
			return new User(in);
		}
		
		public User[] newArray (int size) {
			return new User[size];
		}
	};
}

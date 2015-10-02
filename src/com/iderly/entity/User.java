package com.iderly.entity;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	public static final String ELDER = "elder";
	public static final String CAREGIVER = "caregiver";
	private String deviceId;
	private String type;
	private String name;
	private Photo profPic;
	private ArrayList<Photo> photosGallery;
	
	public User (String deviceId, String type, String name, Photo profPic, ArrayList<Photo> photosGallery) {
		this.deviceId = deviceId;
		this.type = type;
		this.name = name;
		this.profPic = profPic;
		this.photosGallery = photosGallery;
	}
	
	public String getDeviceId () {
		return this.deviceId;
	}
	
	public String getType () {
		return this.type;
	}
	
	public String getName () {
		return this.name;
	}
	
	public Photo getProfPic () {
		return this.profPic;
	}
	
	public ArrayList<Photo> getPhotosGallery () {
		return this.photosGallery;
	}
	
	public void setDeviceId (String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setUserType (String type) {
		this.type = type;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void setProfPic (Photo profPic) {
		this.profPic = profPic;
	}
	
	public void addPhotoGallery (Photo p) {
		this.photosGallery.add(p);
	}
	
	
	
	
	// PARCELABLE IMPLEMENTATION
	public User (Parcel in) {
		this.deviceId = in.readString();
		this.type = in.readString();
		this.name = in.readString();
		this.profPic = (Photo) in.readParcelable(Photo.class.getClassLoader());
		this.photosGallery = (ArrayList<Photo>) in.readArrayList(Photo.class.getClassLoader());
	}
	
	@Override
	public int describeContents () {
		return 0;
	}
	
	@Override
	public void writeToParcel (Parcel dest, int flags) {
		dest.writeString(this.deviceId);
		dest.writeString(this.type);
		dest.writeString(this.name);
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

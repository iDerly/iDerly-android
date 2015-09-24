package com.iderly.entity;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Caregiver extends User implements Parcelable {
	 private ArrayList<User> elders;
	 
	 public Caregiver (String email, String userId, String type, String name, int gamesPlayed, double avgScore, ArrayList<Photo> photosGallery, ArrayList<User> elders) {
		 super(email, userId, type, name, gamesPlayed, avgScore, null, photosGallery);
		 this.elders = elders;
	 }
	 
	 public ArrayList<User> getElders () {
		 return this.elders;
	 }
	 
	 public void setElders (ArrayList<User> elders) {
		 this.elders = elders;
	 }
	 
	 public void addElder (User elder) {
		 this.elders.add(elder);
	 }
	 
	 public void registerElder (String email, String userId, String name, Photo profPic) {
		 User u = new User(email, userId, User.CAREGIVER, name, 0, 0.0, profPic, new ArrayList<Photo> ());
		 this.elders.add(u);
	 }
	 
	 
	 
	 // PARCELABLE IMPLEMENTATION
	 public Caregiver (Parcel in) {
		 super(in.readString(), in.readString(), in.readString(), in.readString(), in.readInt(),
				 in.readDouble(), (Photo) in.readParcelable(Photo.class.getClassLoader()), 
				 (ArrayList<Photo>) in.readArrayList(Photo.class.getClassLoader()));
		 
		this.elders = (ArrayList<User>) in.readArrayList(User.class.getClassLoader());
	 }
	 
	 @Override
	 public int describeContents () {
		 return 0;
	 }
	 
	 @Override
	 public void writeToParcel (Parcel dest, int flags) {
		 dest.writeString(this.getEmail());
		 dest.writeString(this.getUserId());
		 dest.writeString(this.getType());
		 dest.writeString(this.getName());
		 dest.writeInt(this.getGamesPlayed());
		 dest.writeDouble(this.getAvgScore());
		 dest.writeParcelable(this.getProfPic(), flags);
		 dest.writeList(this.getPhotosGallery());
		 dest.writeList(this.getElders());
	 }
	 
	 public static final Parcelable.Creator<Caregiver> CREATOR = new Parcelable.Creator<Caregiver>() {
		 public Caregiver createFromParcel (Parcel in) {
			 return new Caregiver(in);
		 }
		 
		 public Caregiver[] newArray (int size) {
			 return new Caregiver[size];
		 }
	};
}

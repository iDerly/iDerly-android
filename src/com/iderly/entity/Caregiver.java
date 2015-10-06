package com.iderly.entity;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Caregiver extends User implements Parcelable {
	 private ArrayList<User> elders;
	 private String email;
	 
	 public Caregiver (String email, String deviceId, String type, String name, ArrayList<User> elders) {
		 super(deviceId, type, name, null, null);
		 this.elders = elders;
		 this.email = email;
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
	 
	 public void registerElder (String email, String deviceId, String name, Photo profPic) {
		 User u = new User(deviceId, User.CAREGIVER, name, profPic, new ArrayList<Photo> ());
		 this.elders.add(u);
	 }
	 
	 
	 
	 // PARCELABLE IMPLEMENTATION
	 public Caregiver (Parcel in) {
		 super(in.readString(), in.readString(), in.readString(),
				 (Photo) in.readParcelable(Photo.class.getClassLoader()), 
				 (ArrayList<Photo>) in.readArrayList(Photo.class.getClassLoader()));
		 
		this.elders = (ArrayList<User>) in.readArrayList(User.class.getClassLoader());
		this.email = in.readString();
	 }
	 
	 @Override
	 public int describeContents () {
		 return 0;
	 }
	 
	 @Override
	 public void writeToParcel (Parcel dest, int flags) {
		 dest.writeString(this.getDeviceId());
		 dest.writeString(this.getType());
		 dest.writeString(this.getName());
		 dest.writeParcelable(this.getProfPic(), flags);
		 dest.writeList(this.getPhotosGallery());
		 dest.writeList(this.getElders());
		 dest.writeString(this.email);
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

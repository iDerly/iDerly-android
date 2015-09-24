package com.iderly.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.iderly.control.Global;

public class Photo implements Parcelable {
	private String imageBase64;
	
	public Photo (String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	
	public String getImageBase64 () {
		return this.imageBase64;
	}
	
	public Bitmap getImageBitmap () {
		return Global.getImageManager().decodeImageBase64(this.imageBase64);
	}
	
	// PARCELABLE IMPLEMENTATION
	public Photo (Parcel in) {
		this.imageBase64 = in.readString();
	}
	
	@Override
	public int describeContents () {
		return 0;
	}
	
	@Override
	public void writeToParcel (Parcel dest, int flags) {
		dest.writeString(this.imageBase64);
	}
	
	public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
		public Photo createFromParcel (Parcel in) {
			return new Photo(in);
		}
		
		public Photo[] newArray (int size) {
			return new Photo[size];
		}
	};
}

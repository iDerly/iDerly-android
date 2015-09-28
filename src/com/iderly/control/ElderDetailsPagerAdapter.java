package com.iderly.control;

import java.util.ArrayList;

import com.iderly.boundary.ElderPhotoGalleryList;
import com.iderly.boundary.ElderProfileFragment;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ElderDetailsPagerAdapter extends FragmentPagerAdapter {
	private final int NO_OF_TABS = 2;
	private User elder;

	public ElderDetailsPagerAdapter(FragmentManager fm, User e) {
		super(fm);
		this.elder = e;
	}
	
	@Override
	public Fragment getItem (int index) {
		switch (index) {
			case 0:
				ElderProfileFragment e = new ElderProfileFragment();
				Bundle b = new Bundle();
				b.putParcelable("elder", this.elder);
				e.setArguments(b);
				
				return e;
				
			case 1:
				ElderPhotoGalleryList eL = new ElderPhotoGalleryList();
				Bundle b2 = new Bundle();
				b2.putParcelable("elder", this.elder);
				eL.setArguments(b2);
				
				return eL;
				
			default:
				Log.d("DEBUGGING", "ElderDetailsPagerAdapter switch statement");
				break;
		}
		
		return null;
	}
	
	@Override
	public int getCount () {
		return this.NO_OF_TABS;
	}
}
package com.iderly.boundary;

import java.util.ArrayList;

import com.example.iderly.ElderPhotoGalleryDetailsActivity;
import com.iderly.control.ElderPhotoListAdapter;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class ElderPhotoGalleryList extends ListFragment {
	private ArrayList<Photo> photos;
	private User elder;
	private ElderPhotoListAdapter mAdapter;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.elder = this.getArguments().getParcelable("elder");
		this.fetchPhotos();
		
		this.mAdapter = new ElderPhotoListAdapter(this.getActivity(), this.photos);
		this.setListAdapter(mAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Photo p = this.photos.get(position);
		
		Intent intent = new Intent(this.getActivity(), ElderPhotoGalleryDetailsActivity.class);
		intent.putExtra("photo", p);
		
		this.startActivity(intent);
	}
	
	
	// TODO: PETER!
	private void fetchPhotos () {
		// CALL HTTP REQUEST AND ASSIGN TO THE ARRAY LIST OF PHOTOS
	}
}

package com.iderly.boundary;

import java.util.ArrayList;

import com.example.iderly.R;
import com.iderly.control.ElderPhotoListAdapter;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ElderPhotoGalleryList extends ListFragment {
	private ArrayList<Photo> photos;
	private User elder;
	private ElderPhotoListAdapter mAdapter;
	private View headerView = null;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// Set the header view in the list fragment
		if (this.headerView != null) {
			this.getListView().addHeaderView(this.headerView);
		}
		
		this.elder = this.getArguments().getParcelable("elder");
		this.fetchPhotos();
		
		this.mAdapter = new ElderPhotoListAdapter(this.getActivity(), this.photos);
		this.setListAdapter(mAdapter);
	}
	
	
	// This method defines the initial layout of the list fragment
	// It also initiates / inflates the header view which will be set into the list fragment later
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.elder_photo_gallery_list_fragment, container, false);
		
		this.headerView = inflater.inflate(R.layout.photo_gallery_list_header, container, false);
		Button b = (Button) this.headerView.findViewById(R.id.Button_AddPhoto);
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent addPhoto = new Intent (ElderPhotoGalleryList.this.getActivity(), ElderAddPhotoActivity.class);
				startActivity(addPhoto);
			}
		});
		
		return view;
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

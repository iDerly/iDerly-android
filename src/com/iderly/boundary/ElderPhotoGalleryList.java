package com.iderly.boundary;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.iderly.R;
import com.iderly.control.ElderPhotoListAdapter;
import com.iderly.control.Global;
import com.iderly.control.HttpPostRequest;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ElderPhotoGalleryList extends ListFragment {
	public static String postUrl = "http://iderly.kenrick95.org/elder/photos";
	
	private ArrayList<Photo> photos = new ArrayList<Photo>();
	private User elder;
	private ElderPhotoListAdapter mAdapter;
	private View headerView = null;
	
	public static int VIEW_PHOTO = 0x00000001;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.elder = this.getArguments().getParcelable("elder");
		this.fetchPhotos();
	}
	
	
	// This method defines the initial layout of the list fragment
	// It also initiates / inflates the header view which will be set into the list fragment later
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = (View) inflater.inflate(R.layout.elder_photo_gallery_list_fragment, container, false);
		
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Photo p = this.photos.get(position);
		
		Intent intent = new Intent(this.getActivity(), ElderPhotoGalleryDetailsActivity.class);
		intent.putExtra("photo", p);
		intent.putExtra("device_id", elder.getDeviceId());
		
		this.startActivityForResult(intent, VIEW_PHOTO);
//		getActivity().finish();
	}
	
	private void fetchPhotos () {
		ProgressDialog pd = ProgressDialog.show(this.getActivity(), null, "Fetching photos...", true);
		
		String targetUrl = postUrl + "/" + elder.getDeviceId();
		new HttpPostRequest(targetUrl, pd, photos) {
			@Override
			public void onFinish(int statusCode, String responseText) {
				((ProgressDialog) this.mixed[0]).dismiss();
				Log.d("fetch photo", "response: " + responseText);
				if(statusCode == HttpURLConnection.HTTP_OK) {;
					try {
						JSONObject response = new JSONObject(responseText);
						
						ArrayList<Photo> photos = (ArrayList<Photo>) this.mixed[1];
						if(statusCode == HttpURLConnection.HTTP_OK) {
							if(response.getInt("status") == 0) {
								JSONArray messages = response.getJSONArray("message");
								for(int i = 0, size = messages.length(); i < size; ++i) {
									JSONObject message = messages.getJSONObject(i);
									photos.add(new Photo(message.getInt("photo_id"), message.getString("attachment"), message.getString("name"), message.getString("remarks")));
								}
								
								mAdapter = new ElderPhotoListAdapter(ElderPhotoGalleryList.this.getActivity(), photos);
								ElderPhotoGalleryList.this.setListAdapter(mAdapter);
							} else {
								new AlertDialog.Builder(ElderPhotoGalleryList.this.getActivity())
									.setMessage("Error occurred in fetching photos!")
									.setNeutralButton("OK", new OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											dialog.dismiss();
										}
									})
									.show();
							}
						}
					} catch (JSONException e) {
						// As always, either Kenrick or the Internet's fault
					}
				}
			}
		}.addParameter("device_id", elder.getDeviceId())
			.send();
	}
}

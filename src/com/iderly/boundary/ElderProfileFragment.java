package com.iderly.boundary;

import com.example.iderly.R;
import com.iderly.entity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ElderProfileFragment extends Fragment {
	private User elder;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.elder = this.getActivity().getIntent().getExtras().getParcelable("elder");
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.fragment_elder_profile_details, container, false);
	}
	
	public void openEditElderProfile (View v) {
		Intent intent = new Intent(this.getActivity(), EditElderProfileActivity.class);
		intent.putExtra("elder", this.elder);
		
		this.startActivity(intent);
	}
}
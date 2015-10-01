package com.iderly.boundary;

import com.example.iderly.R;
import com.iderly.entity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

public class ElderProfileFragment extends Fragment {
	private User elder;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_elder_profile_details, container, false);
		this.elder = this.getActivity().getIntent().getExtras().getParcelable("elder");
		
		double elderAverageScore = 0.0;
		// FETCH THIS ELDER'S AVERAGE SCORE FROM DATABASE FIRST!!
		// HTTP REQUEST HERE!!
		
		((TextView) view.findViewById(R.id.Text_ElderProfile_DeviceId)).setText(this.elder.getDeviceId());
		((TextView) view.findViewById(R.id.Text_ElderProfile_Name)).setText(this.elder.getName());
		((TextView) view.findViewById(R.id.Text_ElderProfile_AverageScore)).setText("" + elderAverageScore);
		((ImageView) view.findViewById(R.id.ImageView_ElderProfile_ProfilePicture)).setImageBitmap(this.elder.getProfPic().getImageBitmap());
		return view;
	}
	
	public void openEditElderProfile (View v) {
		Intent intent = new Intent(this.getActivity(), EditElderProfileActivity.class);
		intent.putExtra("elder", this.elder);
		
		this.startActivity(intent);
	}
	
	public void deleteElder (View v) {
		// DELETE ELDER HERE!!
		// HTTP REQUEST HERE!!
	}
}
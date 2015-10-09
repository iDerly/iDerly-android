package com.iderly.boundary;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iderly.R;
import com.iderly.control.Global;
import com.iderly.control.HttpPostRequest;
import com.iderly.entity.User;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

public class ElderProfileFragment extends Fragment {
	public static String postUrl = "http://iderly.kenrick95.org/caregiver/delete_elder";
	
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
	
//	public void openEditElderProfile (View v) {
//		Intent intent = new Intent(this.getActivity(), EditElderProfileActivity.class);
//		intent.putExtra("elder", this.elder);
//		
//		this.startActivity(intent);
//	}
	
//	public void deleteElder (View v) {
//		ProgressDialog pd = ProgressDialog.show(this.getActivity(), null, "Deleting this elder...", true);
//		new HttpPostRequest(postUrl, pd) {
//			@Override
//			public void onFinish(int statusCode, String responseText) {
//				((ProgressDialog) this.mixed[0]).dismiss();
//				
//				Log.d("delete elder", "response: " + responseText);
//				if(statusCode == HttpURLConnection.HTTP_OK) {;
//					try {
//						JSONObject response = new JSONObject(responseText);
//						
//						AlertDialog.Builder adb = new AlertDialog.Builder(ElderProfileFragment.this.getActivity());
//						if(response.getInt("status") == 0) {
//							adb.setMessage("Deleting elder is successful!")
//								.setNeutralButton("OK", new OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog,	int which) {
//										dialog.dismiss();
//									}
//								}).show();
//						} else {
//							adb.setMessage(response.getJSONArray("message").getString(0))
//								.setNeutralButton("OK", new OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog, int which) {
//										dialog.dismiss();
//									}
//								}).show();
//						}
//					} catch (JSONException e) {
//						// As always, either Kenrick or the Internet's fault
//					}
//				}
//			}
//		}.addParameter("elder_device_id", elder.getDeviceId())
//			.addParameter("caregiver_device_id", Global.deviceId)
//			.send();
//	}
}
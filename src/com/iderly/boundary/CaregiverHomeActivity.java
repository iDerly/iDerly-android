package com.iderly.boundary;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import junit.framework.Assert;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.ElderListAdapter;
import com.iderly.control.Global;
import com.iderly.control.HttpPostRequest;
import com.iderly.entity.Caregiver;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class CaregiverHomeActivity extends ListActivity {
	public static String getCaregiverPOSTURL = "https://iderly-kenrick95.rhcloud.com/caregiver/view_caregiver_and_elder/" + Global.deviceId;
	private ArrayList<User> eldersList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Adding the "Add Elder" button in the ListActivity header
		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		View header = inflater.inflate(R.layout.caregiver_home_header, lv, false);
		
		Button addElderButton = (Button) header.findViewById(R.id.Button_AddElder);
		addElderButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent addElder = new Intent (CaregiverHomeActivity.this, AddElderFormActivity.class);
				startActivity(addElder);
			}
		});
		
		lv.addHeaderView(header);
		
		ProgressDialog pd = ProgressDialog.show(this, null, "Sorry, more loading...");
		new HttpPostRequest(getCaregiverPOSTURL, pd) {
			@Override
			public void onFinish(int statusCode, String responseText) {
				((ProgressDialog) this.mixed[0]).dismiss();
				Log.d("caregiver home", "response: " + responseText);
				
				if(statusCode == HttpURLConnection.HTTP_OK) {
					try {
						JSONObject response = new JSONObject(responseText);
						
						if(response.getInt("status") == 0) {
							JSONArray messages = response.getJSONArray("message");
							
							JSONObject caregiverJSON = messages.getJSONObject(0);
							
							String email = caregiverJSON.getString("email");
							String name = caregiverJSON.getString("name");
							
							ArrayList<User> elders = new ArrayList<User>();
							
							for(int i = 1, size = messages.length(); i < size; ++i) {
								JSONObject elderJSON = messages.getJSONObject(i);
								elders.add(new User(elderJSON.getString("device_id"), User.ELDER, elderJSON.getString("name"), new Photo(elderJSON.getString("attachment"), null, null), null));
							}
							
							Global.getUserManager().createCaregiver(email, Global.deviceId, name, elders);
							
							CaregiverHomeActivity.this.eldersList = ((Caregiver) (Global.getUserManager().getUser())).getElders();
							ElderListAdapter mAdapter = new ElderListAdapter(CaregiverHomeActivity.this, CaregiverHomeActivity.this.eldersList);
							setListAdapter(mAdapter);
						}
						else {
							
						}
					} catch (JSONException e) {
						// Kenrick -_-
					}
					
				}
			}
		}.send();
		
		// Set the items in the list --> this data must be fetched when login!!
		// So, in this state, tbe getElders() should have had an ArrayList already
		
		// YOU NEED TO FETCH THE DATA FIRST WHEN THE CAREGIVER IS LOGGED IN!!
		// LOAD THE CAREGIVER DATA FROM DB, STORE IT ACCORDINGLY TO A "CAREGIVER" OBJECT, WHICH IS THEN UPCASTED TO A "USER" OBJECT IN USERMANAGER!! --> USE HTTP REQUEST!!!!!
		// THESE FEW LINES OF CODES BELOW IS USED ONLY FOR TESTING --> because don't have elderList data yet
		
//		this.eldersList = new ArrayList<User> ();
//		User u = new User("e@e.com", "test1", User.ELDER, "Tester", 0, 0.0, null, null);
//		
//		for (int i = 0; i < 10; i++) {
//			this.eldersList.add(u);
//		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.caregiver_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	// Defines what to do when the item is clicked
	@Override
	public void onListItemClick (ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		// Creating a new intent --> open when the item is clicked
		Intent intent = new Intent(this, ElderDetailsActivity.class);
		User elder = this.eldersList.get(position);
		intent.putExtra("elder", elder);
		
		startActivity(intent);
	}
}

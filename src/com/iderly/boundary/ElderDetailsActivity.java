package com.iderly.boundary;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.ElderDetailsPagerAdapter;
import com.iderly.control.Global;
import com.iderly.control.HttpPostRequest;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;

public class ElderDetailsActivity extends FragmentActivity implements ActionBar.TabListener {
	public static String postUrl = "http://iderly.kenrick95.org/caregiver/delete_elder";
	
	public static int EDIT_ELDER = 0x00000001;
	public static int DELETE_ELDER_OK = 0x80000000;
	public static int EDIT_ELDER_OK = 0x80000001;
	public static int ELDER_ADD_PHOTO = 0x00000002;
	
	private ViewPager viewPager;
	private ElderDetailsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private User elder;
	private String[] tabs = {"Profile", "Gallery"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_elder_details);
		
		// Fetch Elder from Intent putExtra
		this.elder = this.getIntent().getExtras().getParcelable("elder");
		
		new HttpPostRequest("http://iderly.kenrick95.org/elder/view/" + elder.getDeviceId()) {
			@Override
			public void onFinish(int statusCode, String responseText) {
				if(statusCode == HttpURLConnection.HTTP_OK) {
					try {
						JSONObject response = new JSONObject(responseText);
						
					} catch (JSONException e) {
						// Kenrick -_-
					}
				}
			}
		}.send();
		
		this.actionBar = this.getActionBar();
		this.actionBar.setSubtitle("");
		
		this.viewPager = (ViewPager) findViewById(R.id.pager);
		this.actionBar.removeAllTabs();
		
		// The "elder" object is passed into the constructor
		// This is a way of passing the current clicked "elder" object to the corresponding fragments in the later part
		this.mAdapter = new ElderDetailsPagerAdapter(this.getSupportFragmentManager(), elder);
		this.viewPager.setAdapter(this.mAdapter);
		
		this.actionBar.setHomeButtonEnabled(false);
		this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (String n: this.tabs) {
			Tab t = this.actionBar.newTab().setText(n).setTabListener(ElderDetailsActivity.this);
			this.actionBar.addTab(t);
		}
		
		this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elder_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.ActionBar_AddPhoto) {
			Intent intent = new Intent(this, ElderAddPhotoActivity.class);
			intent.putExtra("device_id", elder.getDeviceId());
			startActivityForResult(intent, ELDER_ADD_PHOTO);
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		this.viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {}
	
	public void openEditElderProfile (View v) {
		Intent intent = new Intent(this, EditElderProfileActivity.class);
		intent.putExtra("elder", this.elder);
		
		this.startActivityForResult(intent, EDIT_ELDER);
	}
	
	public void deleteElder (View v) {
		ProgressDialog pd = ProgressDialog.show(this, null, "Deleting elder...", true);
		new HttpPostRequest(postUrl, pd) {
			@Override
			public void onFinish(int statusCode, String responseText) {
				((ProgressDialog) this.mixed[0]).dismiss();
				
				Log.d("delete elder", "response: " + responseText);
				if(statusCode == HttpURLConnection.HTTP_OK) {;
					try {
						JSONObject response = new JSONObject(responseText);
						
						AlertDialog.Builder adb = new AlertDialog.Builder(ElderDetailsActivity.this);
						if(response.getInt("status") == 0) {
							adb.setMessage("Deleting elder is successful!")
								.setNeutralButton("OK", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,	int which) {
										dialog.dismiss();
										
										ElderDetailsActivity.this.setResult(DELETE_ELDER_OK);
										ElderDetailsActivity.this.finish();
									}
								}).show();
						} else {
							adb.setMessage(response.getJSONArray("message").getString(0))
								.setNeutralButton("OK", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								}).show();
						}
					} catch (JSONException e) {
						// As always, either Kenrick or the Internet's fault
					}
				}
			}
		}.addParameter("elder_device_id", elder.getDeviceId())
			.addParameter("caregiver_device_id", Global.deviceId)
			.send();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("activity result", String.format("%d, %d", requestCode, resultCode));
		if(requestCode == EDIT_ELDER && resultCode == EditElderProfileActivity.EDIT_ELDER_OK) {
			setResult(EDIT_ELDER_OK);
			finish();
		} else if(requestCode == ELDER_ADD_PHOTO && resultCode == ElderAddPhotoActivity.ELDER_ADD_PHOTO_OK) {
			startActivity(getIntent());
			finish();
		} else if(resultCode == ElderPhotoGalleryDetailsActivity.DELETE_PHOTO_OK) {
			startActivity(getIntent());
			finish();
		}
	}
}

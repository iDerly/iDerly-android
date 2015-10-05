package com.iderly.boundary;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.HttpPostRequest;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ElderPhotoGalleryDetailsActivity extends Activity {
	public static String postUrl = "http://iderly.kenrick95.org/elder/delete_photo";
	
	private Photo photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elder_photo_gallery_details);
		
		// This Photo object will be passed to the Extra when the Photo list item is clicked
		this.photo = (Photo) this.getIntent().getExtras().getParcelable("photo");
		((ImageView) findViewById(R.id.ImageView_PhotoGallery_Photo)).setImageBitmap(this.photo.getImageBitmap());;
		((TextView) findViewById(R.id.Text_PhotoGallery_Name)).setText(this.photo.getName());
		((TextView) findViewById(R.id.Text_PhotoGallery_Remarks)).setText(this.photo.getRemarks());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elder_photo_gallery_details, menu);
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
	
	public void openEditPhotoGallery (View view) {
		Intent intent = new Intent(this, ElderPhotoGalleryEditPhotoActivity.class);
		intent.putExtra("photo", this.photo);
		
		this.startActivity(intent);
	}
	 
	public void deletePhotoGallery (View view) {
		// CALL HTTP REQUEST HERE!!
		// Tak usah caps cuk!
		
		ProgressDialog pd = ProgressDialog.show(this, null, "Deleting photo...", true);
		new HttpPostRequest(postUrl, pd) {
			@Override
			public void onFinish(int statusCode, String responseText) {
				((ProgressDialog) this.mixed[0]).dismiss();
				
				Log.d("delete photo", "response: " + responseText);
				if(statusCode == HttpURLConnection.HTTP_OK) {;
					try {
						JSONObject response = new JSONObject(responseText);
						
						AlertDialog.Builder adb = new AlertDialog.Builder(ElderPhotoGalleryDetailsActivity.this);
						if(response.getInt("status") == 0) {
							adb.setMessage("Deleting photo is successful!")
								.setNeutralButton("OK", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,	int which) {
										dialog.dismiss();
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
		}.addParameter("id", String.valueOf(photo.getId()))
			.send();
	}
}

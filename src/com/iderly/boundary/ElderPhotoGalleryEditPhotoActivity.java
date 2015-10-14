package com.iderly.boundary;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.Global;
import com.iderly.control.HttpPostRequest;
import com.iderly.entity.Photo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ElderPhotoGalleryEditPhotoActivity extends Activity {
	public static String postUrl = "http://iderly.kenrick95.org/elder/update_photo";
	
	public static int EDIT_PHOTO_OK = 0x00000001;
	
	private Photo photo;
	private LinearLayout editPhotoMessages;
	private String deviceId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elder_photo_gallery_edit_photo);
		
		this.photo = (Photo) this.getIntent().getExtras().getParcelable("photo");
		this.editPhotoMessages = (LinearLayout) findViewById(R.id.LL_EditPhoto_messages);
		
		deviceId = getIntent().getExtras().getString("device_id");
		
		// CAN ONLY CHANGE NAME AND REMARKS, BUT SHOW THE PHOTO --> PHOTO IS NOT EDITABLE THOUGH
		((ImageView) findViewById(R.id.ImageView_PhotoGallery_EditPhoto)).setImageBitmap(this.photo.getImageBitmap());
		((EditText) findViewById(R.id.EditText_PhotoGallery_Edit_Photo_Name)).setText(this.photo.getName());
		((EditText) findViewById(R.id.EditText_PhotoGallery_EditPhoto_Remarks)).setText(this.photo.getRemarks());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elder_photo_gallery_edit_photo, menu);
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
	
	private void clearMessages() {
        this.editPhotoMessages.removeAllViews();
    }
	
	/**
	 * Put an error message to {@link #createAppointmentMessages}
	 * @param message	the error message
	 */
    private void putMessage(String message) {
        TextView textView = new TextView(this);
        textView.setText("\u2022 " + message);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.editPhotoMessages.addView(textView);
    }
    
    public void saveEditPhotoGallery (View view) {
    	this.clearMessages();
    	int valid = 1;
    	
    	String name = ((EditText) findViewById(R.id.EditText_PhotoGallery_Edit_Photo_Name)).getText().toString();
    	String remarks = ((EditText) findViewById(R.id.EditText_PhotoGallery_EditPhoto_Remarks)).getText().toString();

    	if (name == null || name.isEmpty()) {
    		this.putMessage("Photo name is empty!");
    		valid = 0;
    	}
    	
    	if (remarks == null || remarks.isEmpty()) {
    		this.putMessage("Photo remarks is empty!");
    		valid = 0;
    	}
    	
    	if (valid == 1) {
    		ProgressDialog pd = ProgressDialog.show(this, null, "Editting photo...", true);
    		new HttpPostRequest(postUrl, pd) {
				@Override
				public void onFinish(int statusCode, String responseText) {
					((ProgressDialog) this.mixed[0]).dismiss();
					
					Log.d("edit photo", "response: " + responseText);
					if(statusCode == HttpURLConnection.HTTP_OK) {;
						try {
							JSONObject response = new JSONObject(responseText);
							
							AlertDialog.Builder adb = new AlertDialog.Builder(ElderPhotoGalleryEditPhotoActivity.this);
							if(response.getInt("status") == 0) {
								adb.setMessage("Editting photo is successful!")
									.setNeutralButton("OK", new OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,	int which) {
											setResult(EDIT_PHOTO_OK);
											finish();
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
    		}.addParameter("name", name)
    			.addParameter("remarks", remarks)
    			.addParameter("device_id", deviceId)
    			.addParameter("photo_id", String.valueOf(photo.getId()))
    			.send();
    	}
    }
}

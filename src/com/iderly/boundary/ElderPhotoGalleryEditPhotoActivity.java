package com.iderly.boundary;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.entity.Photo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ElderPhotoGalleryEditPhotoActivity extends Activity {
	private Photo photo;
	private LinearLayout editPhotoMessages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elder_photo_gallery_edit_photo);
		
		this.photo = (Photo) this.getIntent().getExtras().getParcelable("photo");
		this.editPhotoMessages = (LinearLayout) findViewById(R.id.LL_EditPhoto_messages);
		
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
    		// HTTP POST REQUEST HERE TO EDIT!!
    	}
    }
}

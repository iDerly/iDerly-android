package com.iderly.boundary;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ElderPhotoGalleryDetailsActivity extends Activity {
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
	}
}

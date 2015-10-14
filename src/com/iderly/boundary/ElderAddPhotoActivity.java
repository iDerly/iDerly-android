package com.iderly.boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.Global;
import com.iderly.control.HttpPostRequest;
import com.iderly.control.HttpPostRequestListener;
import com.iderly.control.UserManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ElderAddPhotoActivity extends Activity {
	public static String postUrl = "http://iderly.kenrick95.org/elder/add_photo";
	
	/**
	 * Constant definition for image selection for profile picture
	 */
	private static final int SELECT_PICTURE = 1;
	private LinearLayout addPhotoMessages;
	private String pictureString = "";
	private String deviceId;
	
	public static int ELDER_ADD_PHOTO_OK = 0x00000001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elder_add_photo);
		
		// Initialize message box
		this.addPhotoMessages = (LinearLayout) findViewById(R.id.add_photo_messages);
		
		deviceId = getIntent().getExtras().getString("device_id");
		
		// Initialize view
		((ImageView) findViewById(R.id.ImageView_PhotoImage)).setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elder_add_photo, menu);
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
        this.addPhotoMessages.removeAllViews();
    }
	
	/**
	 * Put an error message to {@link #createAppointmentMessages}
	 * @param message	the error message
	 */
    private void putMessage(String message) {
        TextView textView = new TextView(this);
        textView.setText("\u2022 " + message);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.addPhotoMessages.addView(textView);
    }
    
    /**
	 * Opens a built-in activity from which patient can choose the attachment image
	 * @param v	the related View to this event
	 */
    public void onSelectImage (View v) {
    	Intent intent = new Intent();
    	intent.setType("image/*");
    	intent.setAction(Intent.ACTION_GET_CONTENT);
    	startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    
    /**
	 * Handle the result from {@link #onSelectImage(View)}
	 */
	public void onActivityResult (int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
			Uri imageUri = data.getData();
			Bitmap imageBitmap = getThumbnail(imageUri);
			
			if (imageBitmap != null) {
				this.pictureString = Global.getImageManager().encodeImageBase64(imageBitmap);
				((ImageView) findViewById(R.id.ImageView_PhotoImage)).setVisibility(View.VISIBLE);
				((ImageView) findViewById(R.id.ImageView_PhotoImage)).setImageBitmap(imageBitmap);
			}
		}
	}
	
	/* http://stackoverflow.com/questions/3879992/get-bitmap-from-an-uri-android */
	/**
	 * Process the image attachment from {@link #onActivityResult(int, int, Intent)} and compress it
	 * @param uri	the image URI
	 * @return		the compressed image Bitmap 
	 */
	private Bitmap getThumbnail (Uri uri) {
		final int THUMBNAIL_SIZE = 180;
		try {
			InputStream input = this.getContentResolver().openInputStream(uri);
			BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
			
			onlyBoundsOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
			input.close();
			
			if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) return null;
			
			int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ?
								onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
			double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;
			
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
	        bitmapOptions.inDither = true;
	        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
	        
	        input = this.getContentResolver().openInputStream(uri);
	        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
	        input.close();
	        
	        return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get the nearest power of two of the sample ratio
	 * @param ratio	the current ratio
	 * @return		the nearest power of two
	 */
	private int getPowerOfTwoForSampleRatio (double ratio) {
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if (k == 0) return 1;
		else return k;
	}
	
	public void addPhoto (View v) {
		this.clearMessages();
		
		int valid = 1;
		String photoName = ((EditText) findViewById(R.id.AddPhoto_PhotoName_Field)).getText().toString();
		String photoRemarks = ((EditText) findViewById(R.id.AddPhoto_PhotoRemarks_Field)).getText().toString();
		
		// Form validation
		if (photoName == null || photoName.isEmpty()) {
			this.putMessage("Elder's name is empty!");
			valid = 0;
		}
		
		if (photoRemarks == null || photoRemarks.isEmpty()) {
			this.putMessage("Elder's device ID is empty!");
			valid = 0;
		}
		
		if (this.pictureString.isEmpty()) {
			this.putMessage("Elder's profile picture is not selected!");
			valid = 0;
		}
		
		if (valid == 1) {
			ProgressDialog pd = ProgressDialog.show(this, null, "Adding photo...", true);
			new HttpPostRequest(postUrl, pd) {
				@Override
				public void onFinish(int statusCode, String responseText) {
					((ProgressDialog) this.mixed[0]).dismiss();
					
					Log.d("add elder's photo", "response: " + responseText);
					if(statusCode == HttpURLConnection.HTTP_OK) {
						try {
							JSONObject response = new JSONObject(responseText);
							if(response.getInt("status") == 0) {
								new AlertDialog.Builder(ElderAddPhotoActivity.this)
									.setMessage("Adding photo is successful!")
									.setNeutralButton("OK", new OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											setResult(ELDER_ADD_PHOTO_OK);
											finish();
											dialog.dismiss();
										}
									})
									.show();
							} else {
								new AlertDialog.Builder(ElderAddPhotoActivity.this)
									.setMessage(response.getJSONArray("message").getString(0))
									.setNeutralButton("OK", new OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											dialog.dismiss();
										}
									})
									.show();
							}
						} catch (JSONException e) {
							// Kenrick or the Internet's fault
						}
						
					}
				}
			}.addParameter("attachment", pictureString)
				.addParameter("device_id", deviceId)
				.addParameter("name", photoName)
				.addParameter("remarks", photoRemarks)
				.send();
		}
	}
}
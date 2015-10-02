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
import com.iderly.entity.User;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class EditElderProfileActivity extends Activity {
	public static String postUrl = "http://iderly.kenrick95.org/elder/update";
	
	private LinearLayout editElderProfileMessages;
	private User elder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_elder_profile);
		this.elder = this.getIntent().getExtras().getParcelable("elder");
		
		this.editElderProfileMessages = (LinearLayout) findViewById(R.id.add_elder_messages);
		((ImageView) findViewById(R.id.ImageView_EditElder_ElderProfilePicture)).setImageBitmap(this.elder.getProfPic().getImageBitmap());
		((EditText) findViewById(R.id.EditText_EditElder_ElderName)).setText(this.elder.getName());
		((EditText) findViewById(R.id.EditText_EditElder_ElderDeviceId)).setText(this.elder.getDeviceId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_elder_profile, menu);
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
        this.editElderProfileMessages.removeAllViews();
    }
	
	/**
	 * Put an error message to {@link #createAppointmentMessages}
	 * @param message	the error message
	 */
    private void putMessage(String message) {
        TextView textView = new TextView(this);
        textView.setText("\u2022 " + message);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.editElderProfileMessages.addView(textView);
    }
	
	public void saveElderProfile (View view) {
		this.clearMessages();
		
		int valid = 1;
		String elderName = ((EditText) findViewById(R.id.EditText_EditElder_ElderName)).getText().toString();
		String elderDeviceId = ((EditText) findViewById(R.id.EditText_EditElder_ElderDeviceId)).getText().toString();
		
		if (elderName == null || elderName.isEmpty()) {
			this.putMessage("Elder's name is empty!");
			valid = 0;
		}
		
		if (elderDeviceId == null || elderDeviceId.isEmpty()) {
			this.putMessage("Elder's device ID is empty!");
			valid = 0;
		}
		
		if (valid == 1) {
			ProgressDialog pd = ProgressDialog.show(this, null, "Fetching photos...", true);
			new HttpPostRequest(postUrl, pd) {
				@Override
				public void onFinish(int statusCode, String responseText) {
					((ProgressDialog) this.mixed[0]).dismiss();
					
					Log.d("fetch photo", "response: " + responseText);
					if(statusCode == HttpURLConnection.HTTP_OK) {;
						try {
							JSONObject response = new JSONObject(responseText);
							
							// lanjut sini
							
						} catch (JSONException e) {
							// As always, either Kenrick or the Internet's fault
						}
					}
				}
			}.addParameter("device_id", elderDeviceId)
				.addParameter("attachment", "")
				.addParameter("name", elderName)
				.send();
		}
	}
}

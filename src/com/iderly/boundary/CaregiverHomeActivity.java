package com.iderly.boundary;

import java.util.ArrayList;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.ElderListAdapter;
import com.iderly.control.Global;
import com.iderly.entity.Caregiver;
import com.iderly.entity.User;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class CaregiverHomeActivity extends ListActivity {
	private ArrayList<User> eldersList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Adding the "Add Elder" button in the ListActivity header
		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		View header = inflater.inflate(R.layout.caregiver_home_header, lv, false);
		lv.addHeaderView(header);
		
		// Set the items in the list
//		this.eldersList = ((Caregiver) (Global.getUserManager().getUser())).getElders();
		this.eldersList = new ArrayList<User> ();
		User u = new User("e@e.com", "test1", User.ELDER, "Tester", 0, 0.0, null, null);
		
		for (int i = 0; i < 10; i++) {
			this.eldersList.add(u);
		}
	
		ElderListAdapter mAdapter = new ElderListAdapter(this, this.eldersList);
		setListAdapter(mAdapter);
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
	
	public void openAddElderForm (View view) {
		Intent addElder = new Intent (this, AddElderFormActivity.class);
		startActivity(addElder);
	}
}

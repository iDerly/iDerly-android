package com.iderly.boundary;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.iderly.*;
import com.iderly.control.ElderListAdapter;
import com.iderly.control.Global;
import com.iderly.entity.Caregiver;
import com.iderly.entity.User;

public class ElderList extends ListFragment {
	private ArrayList<User> eldersList;
	private ElderListAdapter mAdapter;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.fetchList();
		mAdapter = new ElderListAdapter(getActivity(), this.eldersList);
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick (ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		// Creating a new intent --> open when the item is clicked
		Intent intent = new Intent(getActivity(), ElderProfileDetailActivity.class);
		User elder = this.eldersList.get(position);
		intent.putExtra("elder", elder);
		
		startActivity(intent);
	}
	
	public void fetchList() {
		this.eldersList = ((Caregiver) (Global.getUserManager().getUser())).getElders();
	}
}

package com.iderly.control;


import java.util.List;

import com.iderly.entity.User;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ElderListAdapter extends ArrayAdapter<User> {
	private Context context;
	
	public ElderListAdapter (Context context, List<User> items) {
		super(context, android.R.layout.simple_list_item_1, items);
		this.context = context;
	}
	
	private class ViewHolder {
		private TextView elderIdText;
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent) {
		User u = (User) getItem(position);
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
//			convertView = mInflater.inflate(, parent, false);
			holder = new ViewHolder();
			
//			holder.elderIdText = (TextView) convertView.findViewById();
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (u != null) {
			holder.elderIdText.setText(u.getUserId());
		}
		
		return convertView;
	}
}

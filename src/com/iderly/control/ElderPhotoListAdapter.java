package com.iderly.control;

import java.util.List;

import com.example.iderly.R;
import com.iderly.entity.Photo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ElderPhotoListAdapter extends ArrayAdapter<Photo> {
	private Context context;
	
	public ElderPhotoListAdapter (Context context, List<Photo> items) {
		super(context, android.R.layout.simple_list_item_1, items);
		this.context = context;
	}
	
	private class ViewHolder {
		private TextView photoRemarks;
	}
	
	public View getView (int position, View convertView, ViewGroup parent) {
		Photo p = (Photo) getItem(position);
		
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.elder_photo_list_item, parent, false);
			holder = new ViewHolder();
			
			holder.photoRemarks = (TextView) convertView.findViewById(R.id.Field_PhotoRemarks);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (p != null) {
			holder.photoRemarks.setText(p.getRemarks());
		}
		
		return convertView;
	}
}

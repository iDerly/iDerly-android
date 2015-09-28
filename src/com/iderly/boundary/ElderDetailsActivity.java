package com.iderly.boundary;

import java.util.ArrayList;

import com.example.iderly.R;
import com.example.iderly.R.id;
import com.example.iderly.R.layout;
import com.example.iderly.R.menu;
import com.iderly.control.ElderDetailsPagerAdapter;
import com.iderly.entity.Photo;
import com.iderly.entity.User;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.app.ActionBar.Tab;

public class ElderDetailsActivity extends FragmentActivity implements ActionBar.TabListener {
	private ViewPager viewPager;
	private ElderDetailsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = {"Profile", "Gallery"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_elder_details);
		
		// Fetch Elder from Intent putExtra
		User elder = this.getIntent().getExtras().getParcelable("elder");
		
		this.actionBar = this.getActionBar();
		this.actionBar.setSubtitle("");
		
		this.viewPager = (ViewPager) findViewById(R.id.pager);
		this.actionBar.removeAllTabs();
		
		this.mAdapter = new ElderDetailsPagerAdapter(this.getSupportFragmentManager(), elder);
		this.viewPager.setAdapter(this.mAdapter);
		
		this.actionBar.setHomeButtonEnabled(false);
		this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (String n: this.tabs) {
			Tab t = this.actionBar.newTab().setText(n).setTabListener(ElderDetailsActivity.this);
			this.actionBar.addTab(t);
		}
		
		this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elder_details, menu);
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

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		this.viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {}
}

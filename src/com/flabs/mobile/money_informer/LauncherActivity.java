package com.flabs.mobile.money_informer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.gui.LoadWarningDialog;
import com.flabs.mobile.money_informer.model.Preferences;

public class LauncherActivity extends SherlockFragmentActivity{

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_layout);

		getSupportActionBar().setTitle(R.string.loading);
		
		final Intent mIntent = new Intent();
		mIntent.setClass(getApplicationContext(), MSMainActivity.class);
		
		if((Boolean) Preferences.getPreference(getApplicationContext(), Preferences.KEY_FIRST_BOOT, false)) {

//		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//		alertDialog.setMessage("Press OK to index countries. This only needs to happen once.");
//
//		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				
//				startIntent(mIntent);
//
//			} });
//
//		alertDialog.show();
			
			LoadWarningDialog dialog = new LoadWarningDialog();
			dialog.show(getSupportFragmentManager(), "TAG");
		}
		else {
			startIntent(mIntent);
		}
	}

	public void startIntent(Intent i) {

		startActivity(i);
	}


}

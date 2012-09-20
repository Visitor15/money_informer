package com.flabs.mobile.money_informer.model;

import android.view.View;

public class CountryButtonListener {

	public static final String TAG = "CountryButtonListener";
	
	CountryButton btn;
	
	public CountryButtonListener(View v) {
		btn = (CountryButton) v;
	}
	
//	@Override
//	public void onClick(final View v) {
//		
//		btn.onClickAction(btn);
//	}
	
	public interface OnClickAction {
		public void onClickAction(View v);
	}
}

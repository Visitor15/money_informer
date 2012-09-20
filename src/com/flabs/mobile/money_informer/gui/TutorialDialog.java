package com.flabs.mobile.money_informer.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.flabs.mobile.money.spender.R;

public class TutorialDialog extends SherlockDialogFragment {

	public static final String TAG = "TutorialDialog";
	
	private LinearLayout parent;
	
	public TutorialDialog() {
		
	}
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog);
		setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog);
		
		
	}
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

		parent = (LinearLayout) inflater.inflate(R.layout.tutorial_dialog_layout, container, false);
		
		return parent;
	}
}

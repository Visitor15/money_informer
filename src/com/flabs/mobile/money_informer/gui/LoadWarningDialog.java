package com.flabs.mobile.money_informer.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.LauncherActivity;
import com.flabs.mobile.money_informer.MSMainActivity;

public class LoadWarningDialog extends SherlockDialogFragment {
	
	Button okayBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setStyle(STYLE_NO_FRAME, 0);
		setStyle(STYLE_NO_TITLE, 0);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.load_warning_layout, container, false);
		
		okayBtn = (Button) v.findViewById(R.id.btn_one);
		okayBtn.setText("OK");
		setOkayBtnListener(okayBtn);
		
		return v;
	}
	
	private void setOkayBtnListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent mIntent = new Intent();
				mIntent.setClass(getActivity(), MSMainActivity.class);
				getDialog().dismiss();
				((LauncherActivity) getActivity()).startIntent(mIntent);
			}
			
		});
	}
}

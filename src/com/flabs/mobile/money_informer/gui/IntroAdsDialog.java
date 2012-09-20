package com.flabs.mobile.money_informer.gui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.MSMainActivity;
import com.flabs.mobile.money_informer.model.Preferences;
import com.flabs.mobile.money_informer.utils.Util;

public class IntroAdsDialog extends SherlockDialogFragment {

	public static final String TAG = "IntroAdsDialog";

	Button acceptAds;
	Button upgradeToPro;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setStyle(STYLE_NO_FRAME, 0);
		setStyle(STYLE_NO_TITLE, 0);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.intro_ads_dialog_layout, container, false);

		acceptAds = (Button) v.findViewById(R.id.btn_one);
		upgradeToPro = (Button) v.findViewById(R.id.btn_two);
		
		acceptAds.setText("I don't mind ads");
		upgradeToPro.setText("Upgrade to pro");

		setAcceptAdsListener(acceptAds);
		setUpgradeToProListener(upgradeToPro);
		
		return v;
	}
	
	private void setAcceptAdsListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Preferences.editPreferences(getActivity(), Preferences.KEY_ACCEPTED_ADS, true);
				
				Toast tMessage = new Toast(getActivity());
				tMessage = Toast.makeText(getActivity(),
						"Select the information icon for either country to view more information.",
						Toast.LENGTH_LONG);
				tMessage.setGravity(Gravity.CENTER, 0, 0);
				tMessage.show();
				
				MSMainActivity.stillFirstBoot = true;

				Preferences.editPreferences(getActivity(),
						Preferences.KEY_FIRST_BOOT, false);
				
				getDialog().dismiss();
			}
			
		});
	}
	
	private void setUpgradeToProListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Preferences.editPreferences(getActivity(), Preferences.KEY_ACCEPTED_ADS, false);
//				Toast.makeText(getActivity(), "I will send you to the market", Toast.LENGTH_LONG).show();
				getDialog().dismiss();
				Util.sendToMarket(getActivity());
				
			}
			
		});
	}
}

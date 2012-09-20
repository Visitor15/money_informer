package com.flabs.mobile.money_informer.gui;

import android.content.ComponentName;
import android.content.pm.PackageManager;
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

public class IntroDialog extends SherlockDialogFragment {

	public static final String TAG = "IntroDialog";

	private static boolean hasAccepted = false;

	Button accept;
	Button refuse;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setStyle(STYLE_NO_FRAME, 0);
		setStyle(STYLE_NO_TITLE, 0);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.intro_dialog_layout, container, false);

		accept = (Button) v.findViewById(R.id.btn_one);
		refuse = (Button) v.findViewById(R.id.btn_two);

		accept.setText("Accept");
		refuse.setText("Refuse");

		setAcceptListener(accept);
		setRefuseListener(refuse);

		return v;
	}

	private void setAcceptListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hasAccepted = true;
				Preferences.editPreferences(v.getContext(), Preferences.KEY_EULA_ACCEPT_KEY, true);
				
				if ((Boolean) Preferences.getPreference(getActivity(),
						Preferences.KEY_FIRST_BOOT, false) && Util.shouldNotShowAds(getActivity())) {
					Toast tMessage = new Toast(getActivity());
					tMessage = Toast.makeText(getActivity(),
							"Select the information icon for either country to view more information.",
							Toast.LENGTH_LONG);
					tMessage.setGravity(Gravity.CENTER, 0, 0);
					tMessage.show();
					
					MSMainActivity.stillFirstBoot = true;

					Preferences.editPreferences(getActivity(),
							Preferences.KEY_FIRST_BOOT, false);
				}
				
				getDialog().dismiss();
			}

		});

	}

	private void setRefuseListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hasAccepted = false;
//				Preferences.editPreferences(getActivity(), Preferences.KEY_EULA_ACCEPT_KEY, false);				
				PackageManager pm = getActivity().getPackageManager();
				ComponentName componentName = new ComponentName(getActivity(), MSMainActivity.class);
				pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
						PackageManager.DONT_KILL_APP);
				int flag = 0;
				int newState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
				pm.setComponentEnabledSetting(componentName, newState, flag);
			}

		});
	}

//	@Override
//	public void onDismiss(DialogInterface dialog) {
////		Preferences.editPreferences(getActivity(), Preferences.KEY_EULA_ACCEPT_KEY, hasAccepted);
//		if(hasAccepted) {
//			getDialog().dismiss();
//		}
//		else {
//			//Didn't accept, have to close app.
//			PackageManager pm = getActivity().getPackageManager();
//			ComponentName componentName = new ComponentName(getActivity(), MSMainActivity.class);
//			pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
//					PackageManager.DONT_KILL_APP);
//			int flag = 0;
//			int newState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
//			pm.setComponentEnabledSetting(componentName, newState, flag);
//		}
//	}
}

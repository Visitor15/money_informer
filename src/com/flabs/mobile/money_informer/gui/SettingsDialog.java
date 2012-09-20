package com.flabs.mobile.money_informer.gui;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.model.CountryButton;
import com.flabs.mobile.money_informer.model.Preferences;
import com.flabs.mobile.money_informer.utils.Util;

public class SettingsDialog extends SherlockFragmentActivity implements IFavoritedButtonList {

	public static final String TAG = "SettingsDialog";

	private Context mContext;

	//UI Elements
	private Button btnSave;
	private Button btnCancel;
	private Button btnClearFavorites;
	private LinearLayout llOneDay;
	private TextView tvOneDay;
	private LinearLayout llFiveDays;
	private TextView tvFiveDays;
	private LinearLayout llOneMonth;
	private TextView tvOneMonth;
	private LinearLayout llThreeMonths;
	private TextView tvThreeMonths;
	private LinearLayout llSixMonths;
	private TextView tvSixMonths;
	private LinearLayout llOneYear;
	private TextView tvOneYear;
	private LinearLayout llTwoYears;
	private TextView tvTwoYears;
	private LinearLayout llFiveYears;
	private TextView tvFiveYears;
	private CheckBox cbGoogleLinkCurrency;
	private CheckBox cbGoogleLinkCountry;
	private CheckBox cbWikipediaLinkCurrency;
	private CheckBox cbWikipediaLinkCountry;
	private LinearLayout settingContents;
	private RelativeLayout homeLocation;
	private RelativeLayout travelingLocation;
	private TextView homeLocationValue;
	private TextView travelingLocationValue;
	private boolean isShowingHomeList;
	private boolean isShowingTravelingList;

	private ArrayList<LinearLayout> linearLayoutList = new ArrayList<LinearLayout>();
	private ArrayList<TextView> textViewList = new ArrayList<TextView>();

	private boolean isHome;

	public SettingsDialog() {

	}

	public SettingsDialog(Context c) {
		mContext = getApplicationContext();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_dialog_layout);

		init();
		if(savedInstanceState != null) {
			isShowingHomeList = savedInstanceState.getBoolean("list_key_home");
			isShowingTravelingList = savedInstanceState.getBoolean("list_key_traveling");

			if(isShowingHomeList) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				FavoritedButtonList favList = new FavoritedButtonList(true);
				isHome = false;
				isShowingHomeList = true;
				isShowingTravelingList = false;
				ft.replace(R.id.ll_settings_contents, favList);
				ft.addToBackStack(null);
				ft.setTransition(android.R.anim.slide_in_left);
				ft.commit();
			}
			else if(isShowingTravelingList) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				FavoritedButtonList favList = new FavoritedButtonList(true);
				isHome = false;
				isShowingHomeList = false;
				isShowingTravelingList = true;
				ft.replace(R.id.ll_settings_contents, favList);
				ft.addToBackStack(null);
				ft.setTransition(android.R.anim.slide_in_left);
				ft.commit();
			}
		}
	}

	private void init() {
		mContext = getApplicationContext();
		Preferences.editPreferences(mContext, Preferences.KEY_EULA_ACCEPT_KEY, true);
		getSupportActionBar().setTitle(R.string.settings);
		//		btnSave = (Button) findViewById(R.id.btn_one);
		//		btnCancel = (Button) findViewById(R.id.btn_two);
		cbGoogleLinkCurrency = (CheckBox) findViewById(R.id.cb_google_currency);
		cbGoogleLinkCountry = (CheckBox) findViewById(R.id.cb_google_country);
		cbWikipediaLinkCurrency = (CheckBox) findViewById(R.id.cb_wikipedia_currency);
		cbWikipediaLinkCountry = (CheckBox) findViewById(R.id.cb_wikipedia_country);

		llOneDay = (LinearLayout) findViewById(R.id.ll_one_day);
		tvOneDay = (TextView) llOneDay.findViewById(R.id.tv_text);
		llFiveDays = (LinearLayout) findViewById(R.id.ll_five_days);
		tvFiveDays = (TextView) llFiveDays.findViewById(R.id.tv_text);
		llOneMonth = (LinearLayout) findViewById(R.id.ll_one_month);
		tvOneMonth = (TextView) llOneMonth.findViewById(R.id.tv_text);
		llThreeMonths = (LinearLayout) findViewById(R.id.ll_three_months);
		tvThreeMonths = (TextView) llThreeMonths.findViewById(R.id.tv_text);
		llSixMonths = (LinearLayout) findViewById(R.id.ll_six_months);
		tvSixMonths = (TextView) llSixMonths.findViewById(R.id.tv_text);
		llOneYear = (LinearLayout) findViewById(R.id.ll_one_year);
		tvOneYear = (TextView) llOneYear.findViewById(R.id.tv_text);
		llTwoYears = (LinearLayout) findViewById(R.id.ll_two_years);
		tvTwoYears = (TextView) llTwoYears.findViewById(R.id.tv_text);
		llFiveYears = (LinearLayout) findViewById(R.id.ll_five_years);
        tvFiveYears = (TextView) llFiveYears.findViewById(R.id.tv_text);
		settingContents = (LinearLayout) findViewById(R.id.ll_settings_contents);
		homeLocation = (RelativeLayout) findViewById(R.id.home_location);
		travelingLocation = (RelativeLayout) findViewById(R.id.traveling_location);
		homeLocationValue = (TextView) findViewById(R.id.tv_home_location_value);
		travelingLocationValue = (TextView) findViewById(R.id.tv_traveling_location_value);
		btnClearFavorites = (Button) findViewById(R.id.btn_one);


		linearLayoutList.add(llOneDay);
		linearLayoutList.add(llFiveDays);
		linearLayoutList.add(llOneMonth);
		linearLayoutList.add(llThreeMonths);
		linearLayoutList.add(llSixMonths);
		linearLayoutList.add(llOneYear);
		linearLayoutList.add(llTwoYears);
		linearLayoutList.add(llFiveYears);

		setButtonTexts();

		//		btnSave.setText("Save");
		//		btnCancel.setText("Cancel");
		homeLocationValue.setText((String) Preferences.getPreference(mContext, Preferences.KEY_HOME_LOCATION, false));
		travelingLocationValue.setText((String) Preferences.getPreference(mContext, Preferences.KEY_TRAVELING_LOCATION, false));

		setParams();

		setHomeLocationBtnListener(homeLocation);
		setTravelingLocationBtnListener(travelingLocation);
		setClearFavoritesButtonListener(btnClearFavorites);
		//		setCancelBtnListener(btnCancel);
		//		setSaveBtnListener(btnSave);
		setButtonListeners();
		
		if(Util.shouldNotShowAds(getApplicationContext())) {
			View adView = findViewById(R.id.adView);
			adView.setVisibility(View.GONE);
		}
	}
	
	private void setClearFavoritesButtonListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Util.clearFavoritesForMasterList(mContext);
			}
			
		});
	}

	private void setTravelingLocationBtnListener(RelativeLayout btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				FavoritedButtonList favList = new FavoritedButtonList(true);
				isHome = false;
				isShowingHomeList = false;
				isShowingTravelingList = true;
				ft.replace(R.id.ll_settings_contents, favList);
				ft.addToBackStack(null);
				ft.setTransition(android.R.anim.slide_in_left);
				ft.commit();
				
				Util.getCountryButtonByNameFromMasterList(mContext, travelingLocationValue.getText().toString()).setIsFavorited(false);
			}

		});
	}

	private void setHomeLocationBtnListener(RelativeLayout btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				FavoritedButtonList favList = new FavoritedButtonList(true);
				isHome = true;
				isShowingHomeList = true;
				isShowingTravelingList = false;
				ft.replace(R.id.ll_settings_contents, favList);
				ft.addToBackStack(null);
				ft.setTransition(android.R.anim.slide_in_left);
				ft.commit();
				
				Util.getCountryButtonByNameFromMasterList(mContext, homeLocationValue.getText().toString()).setIsFavorited(false);
			}

		});
	}

	private void setButtonListeners() {
		for(LinearLayout v : linearLayoutList) {
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					clearAllSelected();
					v.setSelected(true);
				}

			});
		}
	}

	private void clearAllSelected() {
		for (LinearLayout v : linearLayoutList) {
			v.setSelected(false);
		}
	}

	private void setButtonTexts() {
		tvOneDay.setText(R.string.one_day);
		textViewList.add(tvOneDay);

		tvFiveDays.setText(R.string.five_days);
		textViewList.add(tvFiveDays);

		tvOneMonth.setText(R.string.one_month);
		textViewList.add(tvOneMonth);

		tvThreeMonths.setText(R.string.three_months);
		textViewList.add(tvThreeMonths);

		tvSixMonths.setText(R.string.six_months);
		textViewList.add(tvSixMonths);

		tvOneYear.setText(R.string.one_year);
		textViewList.add(tvOneYear);

		tvTwoYears.setText(R.string.two_years);
		textViewList.add(tvTwoYears);
		
		tvFiveYears.setText(R.string.five_years);
		textViewList.add(tvFiveYears);

		btnClearFavorites.setText("Clear Favorites");
	}

	private void setParams() {
		String btnSelected = (String) Preferences.getPreference(mContext, Preferences.KEY_GRAPH_TIME_FRAME, false);

		clearAllSelected();
		if(btnSelected.equalsIgnoreCase("1d")) { 
			llOneDay.setSelected(true);
		}
		else if(btnSelected.equalsIgnoreCase("5d")) {
			llFiveDays.setSelected(true);
		}
		else if(btnSelected.equalsIgnoreCase("1m")) {
			llOneMonth.setSelected(true);
		}
		else if(btnSelected.equalsIgnoreCase("3m")) {
			llThreeMonths.setSelected(true);
		}
		else if(btnSelected.equalsIgnoreCase("6m")) {
			llSixMonths.setSelected(true);
		}
		else if(btnSelected.equalsIgnoreCase("1y")) {
			llOneYear.setSelected(true);
		}
		else if(btnSelected.equalsIgnoreCase("2y")) {
			llTwoYears.setSelected(true);
		}
		else if(btnSelected.equalsIgnoreCase("5y")) {
			llFiveYears.setSelected(true);
		}

		cbGoogleLinkCountry.setChecked((Boolean) Preferences.getPreference(mContext, Preferences.KEY_GOOGLE_LINK_COUNTRY, false));
		cbGoogleLinkCurrency.setChecked((Boolean) Preferences.getPreference(mContext, Preferences.KEY_GOOGLE_LINK_CURRENCY, false));
		cbWikipediaLinkCountry.setChecked((Boolean) Preferences.getPreference(mContext, Preferences.KEY_WIKIPEDIA_LINK_COUNTRY, false));
		cbWikipediaLinkCurrency.setChecked((Boolean) Preferences.getPreference(mContext, Preferences.KEY_WIKIPEDIA_LINK_CURRENCY, false));

		cbGoogleLinkCountry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				cbGoogleLinkCurrency.setChecked(false);
				cbGoogleLinkCountry.setChecked(true);
			}

		});

		cbGoogleLinkCurrency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				cbGoogleLinkCountry.setChecked(false);
				cbGoogleLinkCurrency.setChecked(true);
			}

		});


		cbWikipediaLinkCountry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				cbWikipediaLinkCurrency.setChecked(false);
				cbWikipediaLinkCountry.setChecked(true);
			}
		});


		cbWikipediaLinkCurrency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				cbWikipediaLinkCountry.setChecked(false);
				cbWikipediaLinkCurrency.setChecked(true);
			}
		});
	}

	private String[] getParamsSelected() {
		for(LinearLayout v : linearLayoutList) {
			if(v.isSelected()) {
				return extractText((TextView) v.findViewById(R.id.tv_text));
			}
			continue;
		}

		return new String[0];
	}

	private String[] extractText(TextView tv) {
		String[] text = new String[3];
		String tempText = tv.getText().toString();

		if(tempText.equalsIgnoreCase(getString(R.string.one_day))) {
			text[0] = tempText.substring(0, 1);
			text[1] = tempText.substring(2, 3);
			text[2] = getString(R.string.one_day);
		}
		else if(tempText.equalsIgnoreCase(getString(R.string.five_days))) {
			text[0] = tempText.substring(0, 1);
			text[1] = tempText.substring(2, 3);
			text[2] = getString(R.string.five_days);
		}
		else if(tempText.equalsIgnoreCase(getString(R.string.one_week))) {
			text[0] = tempText.substring(0, 1);
			text[1] = tempText.substring(2, 3);
			text[2] = getString(R.string.one_week);
		}
		else if(tempText.equalsIgnoreCase(getString(R.string.ten_weeks))) {
			text[0] = tempText.substring(0, 2);
			text[1] = tempText.substring(3, 4);
			text[2] = getString(R.string.ten_weeks);
		}
		else if(tempText.equalsIgnoreCase(getString(R.string.one_month))) {
			text[0] = tempText.substring(0, 1);
			text[1] = tempText.substring(2, 3);
			text[2] = getString(R.string.one_month);
		}
		else if(tempText.equalsIgnoreCase(getString(R.string.three_months))) {
			text[0] = tempText.substring(0, 1);
			text[1] = tempText.substring(2, 3);
			text[2] = getString(R.string.three_months);
		}
		else if(tempText.equalsIgnoreCase(getString(R.string.six_months))) {
			text[0] = tempText.substring(0, 1);
			text[1] = tempText.substring(2, 3);
			text[2] = getString(R.string.six_months);
		}
		else if(tempText.equalsIgnoreCase(getString(R.string.one_year))) {
			text[0] = tempText.substring(0, 1);
			text[1] = tempText.substring(2, 3);
			text[2] = getString(R.string.one_year);
		}
		else if(tempText.equalsIgnoreCase(getString(R.string.two_years))) {
			text[0] = tempText.substring(0, 1);
			text[1] = tempText.substring(2, 3);
			text[2] = getString(R.string.two_years);
		}
		 else if(tempText.equalsIgnoreCase(getString(R.string.five_years))) {
			 text[0] = tempText.substring(0, 1);
			 text[1] = tempText.substring(2, 3);
			 text[2] = getString(R.string.five_years);
		 }

		return text;
	}

	private void setCancelBtnListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
	}

	private void setSaveBtnListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] graphTime = getParamsSelected();
				Preferences.editPreferences(mContext, Preferences.KEY_GRAPH_TIME_FRAME, graphTime[0].concat(graphTime[1]));
				Preferences.editPreferences(mContext, Preferences.KEY_GOOGLE_LINK_COUNTRY, cbGoogleLinkCountry.isChecked());
				Preferences.editPreferences(mContext, Preferences.KEY_GOOGLE_LINK_CURRENCY, cbGoogleLinkCurrency.isChecked());
				Preferences.editPreferences(mContext, Preferences.KEY_WIKIPEDIA_LINK_COUNTRY,cbWikipediaLinkCountry.isChecked());
				Preferences.editPreferences(mContext, Preferences.KEY_WIKIPEDIA_LINK_CURRENCY,cbWikipediaLinkCurrency.isChecked());


				finish();
			}

		});
	}

	private void savePreferences() {
		String[] graphTime = getParamsSelected();
		Preferences.editPreferences(mContext, Preferences.KEY_GRAPH_TIME_FRAME, graphTime[0].concat(graphTime[1]));
		Preferences.editPreferences(mContext, Preferences.KEY_GOOGLE_LINK_COUNTRY, cbGoogleLinkCountry.isChecked());
		Preferences.editPreferences(mContext, Preferences.KEY_GOOGLE_LINK_CURRENCY, cbGoogleLinkCurrency.isChecked());
		Preferences.editPreferences(mContext, Preferences.KEY_WIKIPEDIA_LINK_COUNTRY,cbWikipediaLinkCountry.isChecked());
		Preferences.editPreferences(mContext, Preferences.KEY_WIKIPEDIA_LINK_CURRENCY,cbWikipediaLinkCurrency.isChecked());
	}

	@Override
	public void onSaveInstanceState(Bundle state) {
		state.putBoolean("list_key_home", isShowingHomeList);
		state.putBoolean("list_key_traveling", isShowingTravelingList);
	}

	@Override
	public void onPause() {
		super.onPause();
		savePreferences();
	}

	@Override
	public void handleOnClick(CountryButton btn, FavoritedButtonList list) {
		getSupportFragmentManager().popBackStack();

		Util.getCountryButtonByNameFromMasterList(mContext, btn.getLabel()).setIsFavorited(true);
		
		isShowingHomeList = false;
		isShowingTravelingList = false;

		if(isHome) {
			homeLocationValue.setText(btn.getLabel());
			Preferences.editPreferences(mContext, Preferences.KEY_HOME_LOCATION, btn.getLabel());
		}
		else {
			travelingLocationValue.setText(btn.getLabel());
			Preferences.editPreferences(mContext, Preferences.KEY_TRAVELING_LOCATION, btn.getLabel());
		}
	}
}

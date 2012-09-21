package com.flabs.mobile.money_informer;

import java.net.URLEncoder;
import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.gui.CurrencyListFragment;
import com.flabs.mobile.money_informer.gui.FavoritesDialog;
import com.flabs.mobile.money_informer.gui.FullScreenImageViewer;
import com.flabs.mobile.money_informer.gui.InfoCardFragment;
import com.flabs.mobile.money_informer.gui.IntroAdsDialog;
import com.flabs.mobile.money_informer.gui.IntroDialog;
import com.flabs.mobile.money_informer.gui.SettingsDialog;
import com.flabs.mobile.money_informer.model.ChartDownloaderTask;
import com.flabs.mobile.money_informer.model.CountryButton;
import com.flabs.mobile.money_informer.model.ExchangeRateDownloaderTask;
import com.flabs.mobile.money_informer.model.IExchangeRateTask;
import com.flabs.mobile.money_informer.model.Preferences;
import com.flabs.mobile.money_informer.model.TutorialFlasherTask;
import com.flabs.mobile.money_informer.utils.Util;

public class MSMainActivity extends SherlockFragmentActivity implements
		CurrencyListFragment.CurrencyFragmentListener, IExchangeRateTask {

	public static final String TAG = "MSMainActivity";

	public static final String BUNDLE_KEY = "bundle_key";
	public static final String KEY_COLUMN_ID = "column_id";
	public static final String KEY_NAME = "key_name";

	public static final int REQUEST_CODE = 0;

	public CurrencyListFragment currencyFrom;
	public CurrencyListFragment currencyTo;
	InfoCardFragment infoFrag;

	EditText input;
	TextView exchangeRate;
	FrameLayout handleButton;
	FrameLayout footerHandleButton;
	ImageView handleIcon;
	ImageView footerHandleIcon;
	LinearLayout listContainer;
	LinearLayout headerContainer;
	LinearLayout footerContainer;
	LinearLayout infoFragHeader;
	RelativeLayout header;
	RelativeLayout contentsContainer;
	TextView convertBtn;
	ImageView currencyChart;
	LinearLayout ribbon;
	FrameLayout dataPanel;
	MSMainActivity mActivity;

	TutorialFlasherTask tutorial;

	private static boolean chartIsShowing = false;
	private static boolean shouldShowFullScreenChart = true;
	public static boolean stillFirstBoot = false;
	private double userInput;

	private static boolean isShowing;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		isShowing = false;
		getSupportActionBar().setTitle(R.string.app_name);
		setContentView(R.layout.activity_msmain);
		


//		if ((Boolean) Preferences.getPreference(getApplicationContext(),
//				Preferences.KEY_FIRST_BOOT, false)) {
//			Toast.makeText(getApplicationContext(),
//					"Indexing countries. This only needs to happen once.",
//					Toast.LENGTH_LONG).show();
//		}

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.activity_msmain, menu);
		
		if(Util.hasKeyInstalled(mActivity)) {
			menu.removeItem(R.id.menu_upgrade_to_pro);
		}
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			NavUtils.navigateUpFromSameTask(this);

			return true;
		}
		case R.id.menu_favorites: {
			Intent mIntent = new Intent();
			mIntent.setClass(getApplicationContext(), FavoritesDialog.class);
			startActivityForResult(mIntent, REQUEST_CODE);

			return true;
		}
		case R.id.menu_settings: {
			Intent mIntent = new Intent();
			mIntent.setClass(getApplicationContext(), SettingsDialog.class);
			startActivity(mIntent);

			return true;
		}
		case R.id.menu_feedback: {
			String uriText = "mailto:forge.labs@gmail.com"
					+ "?subject="
					+ URLEncoder.encode("Money Informer: FEEDBACK")
					+ "&body="
					+ URLEncoder
							.encode("Go ahead, give me your opinions!\n\n\n");

			Uri uri = Uri.parse(uriText);

			Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
			sendIntent.setData(uri);
			startActivity(Intent.createChooser(sendIntent, "Send email"));
			return true;
		}
		case R.id.menu_upgrade_to_pro: {
			Util.sendToMarket(mActivity);
			
			return true;
		}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		boolean isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

		if (shouldShowFullScreenChart && chartIsShowing && isLandScape) {
			FullScreenImageViewer imageViewerFrag = new FullScreenImageViewer(
					infoFrag.getDisplayingChart());
			imageViewerFrag.show(getSupportFragmentManager(), TAG);
			shouldShowFullScreenChart = false;
		} else {
			shouldShowFullScreenChart = true;
		}
	}

	public void startChartDownloadTask(String isoFrom, String isoTo) {
		String mUrl = Util.getChartUrl(isoFrom, isoTo, (String) Preferences
				.getPreference(getApplicationContext(),
						Preferences.KEY_GRAPH_TIME_FRAME, false));

		ChartDownloaderTask task = new ChartDownloaderTask(
				infoFrag.getChartView(), mUrl);
		task.execute();
	}

	private void init() {
		mActivity = this;
		infoFrag = new InfoCardFragment();

		CountryButton leftBtn = Util.getCountryButtonByNameFromMasterList(
				mActivity, (String) Preferences.getPreference(mActivity,
						Preferences.KEY_HOME_LOCATION, false));
		CountryButton rightBtn = Util.getCountryButtonByNameFromMasterList(
				mActivity, (String) Preferences.getPreference(mActivity,
						Preferences.KEY_TRAVELING_LOCATION, false));

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		ft.replace(R.id.header, infoFrag);
		ft.commit();

		currencyFrom = new CurrencyListFragment(CurrencyListFragment.LEFT_COL,
				leftBtn);
		currencyTo = new CurrencyListFragment(CurrencyListFragment.RIGHT_COL,
				rightBtn);

		ft = getSupportFragmentManager().beginTransaction();

		ft.replace(R.id.rl_left_side, currencyFrom);
		ft.replace(R.id.rl_right_side, currencyTo);

		ft.commit();

		handleButton = (FrameLayout) findViewById(R.id.fl_hide_lists_handle);
		listContainer = (LinearLayout) findViewById(R.id.ll_currency_list_container);
		header = (RelativeLayout) findViewById(R.id.header);
		contentsContainer = (RelativeLayout) findViewById(R.id.rl_contents_container);
		handleIcon = (ImageView) findViewById(R.id.iv_handle_icon);
		convertBtn = (TextView) findViewById(R.id.iv_convert_btn);
		input = (EditText) findViewById(R.id.et_input);
		headerContainer = (LinearLayout) findViewById(R.id.header_container);
		footerContainer = (LinearLayout) findViewById(R.id.footer_container);
		footerHandleButton = (FrameLayout) footerContainer
				.findViewById(R.id.fl_hide_lists_handle);
		footerHandleIcon = (ImageView) footerContainer
				.findViewById(R.id.iv_handle_icon);
		infoFragHeader = (LinearLayout) headerContainer
				.findViewById(R.id.ll_info_frag_container);
		exchangeRate = (TextView) headerContainer
				.findViewById(R.id.tv_exchange_rate);

		handleIcon.setBackgroundResource(R.drawable.down_arrow_white);
		footerHandleIcon.setBackgroundResource(R.drawable.down_arrow_white);
		setConvertBtnListener(convertBtn);
		setHandleListener(handleButton);
		setFooterHandleListener(footerHandleButton);
		
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!((Boolean) Preferences.getPreference(getApplicationContext(),
				Preferences.KEY_ACCEPTED_ADS, false))
				&& !(Util.shouldNotShowAds(getApplicationContext()))) {
			IntroAdsDialog adsDialog = new IntroAdsDialog();
			adsDialog.show(getSupportFragmentManager(), TAG);
		} else {
			if (Util.shouldNotShowAds(getApplicationContext())) {
				View adView = findViewById(R.id.adView);
				adView.setVisibility(View.GONE);
			} else {
				View adView = findViewById(R.id.adView);
				adView.setVisibility(View.VISIBLE);
			}
		}

		if (!((Boolean) Preferences.getPreference(getApplicationContext(),
				Preferences.KEY_EULA_ACCEPT_KEY, false))) {
			IntroDialog intro = new IntroDialog();
			intro.show(getSupportFragmentManager(), TAG);
		}
	}

	private void setConvertBtnListener(TextView btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ExchangeRateDownloaderTask task = new ExchangeRateDownloaderTask(
						mActivity, currencyFrom.getSelectedCountry()
								.getISOCode(), currencyTo.getSelectedCountry()
								.getISOCode());
				task.execute(Util.baseURL
						.concat(currencyFrom.getSelectedCountry().getISOCode())
						.concat(currencyTo.getSelectedCountry().getISOCode())
						.concat("=X"));
			}

		});
	}

	private void handleBundle(Bundle b) {
		int colId = b.getInt(KEY_COLUMN_ID);
		String countryName = b.getString(KEY_NAME);

		switch (colId) {
		case CurrencyListFragment.LEFT_COL: {
			setInfoCardData(CurrencyListFragment.LEFT_COL,
					getButtonByCountryName(countryName));
			currencyFrom.setButtonSelected(countryName);
			break;
		}
		case CurrencyListFragment.RIGHT_COL: {
			setInfoCardData(CurrencyListFragment.RIGHT_COL,
					getButtonByCountryName(countryName));
			currencyTo.setButtonSelected(countryName);
			break;
		}
		}

		ExchangeRateDownloaderTask task = new ExchangeRateDownloaderTask(
				mActivity, currencyFrom.getSelectedCountry().getISOCode(),
				currencyTo.getSelectedCountry().getISOCode());
		task.execute(Util.baseURL
				.concat(currencyFrom.getSelectedCountry().getISOCode())
				.concat(currencyTo.getSelectedCountry().getISOCode())
				.concat("=X"));
	}

	private void setFooterHandleListener(FrameLayout button) {
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout inputContainer = (LinearLayout) footerContainer
						.findViewById(R.id.ll_input_container);
				FrameLayout dropShadow = (FrameLayout) footerContainer
						.findViewById(R.id.fl_footer_drop_shadow);
				if (inputContainer.isShown()) {
					inputContainer.setVisibility(View.GONE);
					dropShadow.setVisibility(View.GONE);
					footerContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT, 0));
					contentsContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 0, 1f));
					footerHandleIcon
							.setBackgroundResource(R.drawable.up_arrow_white);
				} else {
					contentsContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 0, 0.85f));
					footerContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 0, 0.15f));
					inputContainer.setVisibility(View.VISIBLE);
					dropShadow.setVisibility(View.VISIBLE);
					footerHandleIcon
							.setBackgroundResource(R.drawable.down_arrow_white);
				}
			}

		});
	}

	private void setHandleListener(FrameLayout button) {
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listContainer.isShown()) {
					TutorialFlasherTask.isRunning = false;
					chartIsShowing = true;
					shouldShowFullScreenChart = true;

					Log.d(TAG, "NCC - ISOCODE is: "
							+ currencyFrom.getSelectedCountry().getISOCode());

					ExchangeRateDownloaderTask task = new ExchangeRateDownloaderTask(
							mActivity, currencyFrom.getSelectedCountry()
									.getISOCode(), currencyTo
									.getSelectedCountry().getISOCode());
					task.execute(Util.baseURL
							.concat(currencyFrom.getSelectedCountry()
									.getISOCode())
							.concat(currencyTo.getSelectedCountry()
									.getISOCode()).concat("=X"));

					startChartDownloadTask(currencyFrom.getSelectedCountry()
							.getISOCode(), currencyTo.getSelectedCountry()
							.getISOCode());
					dataPanel = (FrameLayout) findViewById(R.id.panel_data);
					listContainer.setVisibility(View.GONE);
					footerContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 0, 0f));
					Log.d(TAG,
							"NCC - Setting time length to: "
									+ Util.getTimeLengthString(getApplicationContext()));
					infoFrag.setTimeLenght(Util
							.getTimeLengthString(getApplicationContext()));
					infoFrag.getRootView().setLayoutParams(
							new FrameLayout.LayoutParams(
									FrameLayout.LayoutParams.MATCH_PARENT,
									FrameLayout.LayoutParams.WRAP_CONTENT));
					contentsContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 0, 1f));

					headerContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 0, 1f));
					dataPanel.setVisibility(View.VISIBLE);
					handleIcon.setBackgroundResource(R.drawable.up_arrow_white);

					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
					
					if (stillFirstBoot) {
						Toast tMessage = new Toast(mActivity);
						tMessage = Toast.makeText(mActivity,
								"Rotate your device to view chart fullscreen.",
								Toast.LENGTH_LONG);
						tMessage.setGravity(Gravity.CENTER, 0, 0);
						tMessage.show();

						stillFirstBoot = false;
					}

				} else {

					chartIsShowing = false;
					shouldShowFullScreenChart = false;

					dataPanel = (FrameLayout) findViewById(R.id.panel_data);
					listContainer.setVisibility(View.VISIBLE);
					footerContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 0, 0.15f));

					infoFrag.getRootView().setLayoutParams(
							new FrameLayout.LayoutParams(
									FrameLayout.LayoutParams.MATCH_PARENT,
									FrameLayout.LayoutParams.MATCH_PARENT));
					headerContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT, 0f));

					contentsContainer.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 0, 0.85f));

					LinearLayout inputContainer = (LinearLayout) footerContainer
							.findViewById(R.id.ll_input_container);
					FrameLayout dropShadow = (FrameLayout) footerContainer
							.findViewById(R.id.fl_footer_drop_shadow);
					inputContainer.setVisibility(View.VISIBLE);
					dropShadow.setVisibility(View.VISIBLE);
					footerHandleIcon
							.setBackgroundResource(R.drawable.down_arrow_white);

					dataPanel.setVisibility(View.GONE);
					handleIcon
							.setBackgroundResource(R.drawable.down_arrow_white);
				}

				handleButton.bringToFront();
			}

		});
	}

	public CountryButton getButtonByCountryName(String name) {

		ArrayList<CountryButton> list = Util
				.getMasterList(getApplicationContext());
		for (CountryButton btn : list) {
			if (btn.getLabel().equalsIgnoreCase(name)) {
				return btn;
			} else {
				continue;
			}
		}
		return null;
	}

	private double sanatizeInput() {
		if (input.getText().length() > 0) {

			userInput = Double.parseDouble(input.getText().toString());
		} else {
			userInput = 0.0;
		}

		return userInput;
	}

	@Override
	public void setTitleText(int colId, String text) {
		switch (colId) {
		case CurrencyListFragment.LEFT_COL: {
			infoFrag.setTitleLeftText(text);
			break;
		}
		case CurrencyListFragment.RIGHT_COL: {
			infoFrag.setTitleRightText(text);
			break;
		}
		}
	}

	@Override
	public void setInfoCardData(int colId, CountryButton btn) {
		Log.d(TAG, "NCC - BTN: " + btn);

		switch (colId) {
		case CurrencyListFragment.LEFT_COL: {
			infoFrag.setCountryCode(btn.getCountryCode());
			infoFrag.setCurrencyCode(btn.getISOCode());
			infoFrag.setLeftCountryName(btn.getLabel());
			infoFrag.setTitleLeftText(btn.getSymbol().concat(
					btn.getCountryCode()));
			infoFrag.setSubTitleLeftText(btn.getLabel());
			break;
		}
		case CurrencyListFragment.RIGHT_COL: {
			infoFrag.setCountryCode(btn.getCountryCode());
			infoFrag.setCurrencyCode(btn.getISOCode());
			infoFrag.setRightCountryName(btn.getLabel());
			infoFrag.setTitleRightText(btn.getSymbol().concat(
					btn.getCountryCode()));
			infoFrag.setSubTitleRightText(btn.getLabel());
			break;
		}
		}

		// Call from currencyFrom or currencyTo can throw a null on first boot.
		// So we catch it.
		try {
			ExchangeRateDownloaderTask task = new ExchangeRateDownloaderTask(
					mActivity, currencyFrom.getSelectedCountry().getISOCode(),
					currencyTo.getSelectedCountry().getISOCode());
			task.execute(Util.baseURL
					.concat(currencyFrom.getSelectedCountry().getISOCode())
					.concat(currencyTo.getSelectedCountry().getISOCode())
					.concat("=X"));
		} catch (NullPointerException e) {

		}

	}

	@Override
	public void setExchangeRate(Double rate) {
		exchangeRate.setText(Double.toString(rate));
	}

	@Override
	public void updateUI(Double rate, boolean isPreExecute, boolean shouldBlink) {
		if (!isShowing) {
			// setContentView(R.layout.activity_msmain);
			isShowing = true;
		}
		ProgressBar pb = (ProgressBar) headerContainer
				.findViewById(R.id.pb_exchange_rate);
		if (isPreExecute) {
			pb.setVisibility(View.VISIBLE);
		} else {
			Log.d(TAG, "NCC - RATE IS: " + rate);
			exchangeRate.setText(Double.toString(rate));
			sanatizeInput();
			infoFrag.setLeftCurrencyText(userInput);
			infoFrag.setRightCurrencyText(userInput, rate);
			pb.setVisibility(View.INVISIBLE);
			
			currencyFrom.setButtonSelected(infoFrag.getLeftCountryName());
			currencyFrom.showSelectedButtonInList();
			currencyTo.setButtonSelected(infoFrag.getRightCountryName());
			currencyTo.showSelectedButtonInList();
		}

		if (shouldBlink) {
			ArrayList<View> vList = new ArrayList<View>();
			vList.add(infoFrag.getLeftButton());
			vList.add(infoFrag.getRightButton());
			vList.add(handleButton);
			vList.add(footerHandleButton);

			tutorial = new TutorialFlasherTask(vList);
			tutorial.execute(null, null, null);
		}
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			handleBundle(data.getExtras());
		} catch (NullPointerException e) {
			// If the user presses cancel. We'll get a null here.
		}
	}
}

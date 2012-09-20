package com.flabs.mobile.money_informer.gui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.model.CountryButton;
import com.flabs.mobile.money_informer.model.Preferences;

public class InfoSelectorDialog extends SherlockDialogFragment {

	public static final String TAG = "InfoSelectorDialog";

	private static final String BASE_WIKIPEDIA_URL = "http://m.wikipedia.org/wiki/";
	private static final String BASE_GOOGLE_URL = "http://www.google.com/search?q=";

	private CountryButton countryBtn;

	private ImageView btnWikipedia;
	private ImageView btnGoogle;
	private ImageView btnFavorite;
	private Button btnCancel;
	private TextView mTitle;

	private String titleText;
	private String countryCode;
	private String countryName;
	private boolean isFavorited;

	public InfoSelectorDialog() {

	}

	public InfoSelectorDialog(CountryButton btn) {
		countryBtn = btn;
		titleText = btn.getLabel();
		countryCode = btn.getCountryCode();
		countryName = btn.getLabel();
	}

	public InfoSelectorDialog(String titleText, String countryCode, String countryName) {
		this.titleText = titleText;
		this.countryCode = countryCode;
		this.countryName = countryName;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setStyle(STYLE_NO_FRAME, 0);
		setStyle(STYLE_NO_TITLE, 0);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.info_chooser_layout, container, false);

		btnWikipedia = (ImageView) v.findViewById(R.id.iv_wikipedia);
		btnGoogle = (ImageView) v.findViewById(R.id.iv_google);
		btnFavorite = (ImageView) v.findViewById(R.id.iv_favorite);
		btnCancel = (Button) v.findViewById(R.id.btn_one);
		mTitle = (TextView) v.findViewById(R.id.tv_title);

		if(titleText.length() > 0) {
			mTitle.setText(titleText);
		}

		btnCancel.setText(R.string.cancel);
		setCancelListener(btnCancel);
		setWikipediaListener(btnWikipedia);
		setGoogleListener(btnGoogle);
		setFavoriteListener(btnFavorite);

		return v;
	}

	public void setTitleText(String text) {
		mTitle.setText(text);
	}

	private void setWikipediaListener(ImageView v) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "";
				if((Boolean) Preferences.getPreference(getActivity(), Preferences.KEY_WIKIPEDIA_LINK_COUNTRY, false)) {
					url = BASE_WIKIPEDIA_URL.concat(sanatizeWikipediaString(countryName));
				}
				else {
					url = BASE_WIKIPEDIA_URL.concat(countryBtn.getISOCode());
				}
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}

		});
	}

	private void setGoogleListener(ImageView v) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "";
				if((Boolean) Preferences.getPreference(getActivity(), Preferences.KEY_GOOGLE_LINK_COUNTRY, false)) {
					url = BASE_GOOGLE_URL.concat(sanatizeGoogleString(countryName));
				}
				else {
					url = BASE_GOOGLE_URL.concat(countryBtn.getISOCode());
				}
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}

		});
	}

	private void setFavoriteListener(ImageView v) {
		if(countryBtn.isFavorited()) {
			isFavorited = true;
			((ImageView) v).setImageResource(R.drawable.favorited_icon);
		}
		else {
			isFavorited = false;
			((ImageView) v).setImageResource(R.drawable.unfavorited_icon);
		}
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isFavorited) {
					((ImageView) v).setImageResource(R.drawable.unfavorited_icon);
					isFavorited = false;
				}
				else {
					((ImageView) v).setImageResource(R.drawable.favorited_icon);
					isFavorited = true;
				}

				if(countryBtn != null) {
					countryBtn.setIsFavorited(isFavorited);
					Preferences.editPreferences(getActivity(), countryBtn.getLabel(), isFavorited);
				}
			}

		});
	}

	private void setCancelListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}

		});
	}

	private String sanatizeGoogleString(String s) {
		String rebuiltString = "";

		//This is just a hack because it happens for only two countries
		if(s.equalsIgnoreCase("Congo - Kinshasa")) {
			return "Democratic+Republic+of+the+Congo";
		}
		if(s.equalsIgnoreCase("Congo - Brazzaville")) {
			return "Republic+of+the+Congo";
		}

		if(s.indexOf("[") > -1) {
			for(int i = 0; i < s.length(); i++) {
				if(s.substring(i, (i + 1)).equalsIgnoreCase(" ")) {
					rebuiltString = rebuiltString.concat("+");
				}
				else if(s.substring(i, (i + 1)).equalsIgnoreCase("[")) {
					rebuiltString = rebuiltString.concat("(");
				}
				else if(s.substring(i, (i + 1)).equalsIgnoreCase("]")) {
					rebuiltString = rebuiltString.concat(")");
				}
				else {
					rebuiltString = rebuiltString.concat(s.substring(i, (i + 1)));
				}
			}
		}
		else {
			s.replace(" ", "+");
			rebuiltString = s;
		}

		return rebuiltString;
	}

	private String sanatizeWikipediaString(String s) {
		String rebuiltString = "";

		//This is just a hack because it happens for only two countries
		if(s.equalsIgnoreCase("Congo - Kinshasa")) {
			return "Democratic_Republic_of_the_Congo";
		}
		if(s.equalsIgnoreCase("Congo - Brazzaville")) {
			return "Republic_of_the_Congo";
		}

		if(s.indexOf("[") > -1) {
			for(int i = 0; i < s.length(); i++) {
				if(s.substring(i, (i + 1)).equalsIgnoreCase(" ")) {
					rebuiltString = rebuiltString.concat("_");
				}
				else if(s.substring(i, (i + 1)).equalsIgnoreCase("[")) {
					rebuiltString = rebuiltString.concat("(");
				}
				else if(s.substring(i, (i + 1)).equalsIgnoreCase("]")) {
					rebuiltString = rebuiltString.concat(")");
				}
				else {
					rebuiltString = rebuiltString.concat(s.substring(i, (i + 1)));
				}
			}
		}
		else {
			s.replace(" ", "_");
			rebuiltString = s;
		}

		return rebuiltString;
	}
}

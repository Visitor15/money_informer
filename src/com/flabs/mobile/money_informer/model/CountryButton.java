package com.flabs.mobile.money_informer.model;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flabs.mobile.money.spender.R;

public class CountryButton extends LinearLayout implements ICountryButton {

	public final static String TAG = "CountryButton";

	private boolean isSelected = false;
	private boolean isFavorited = false;
	private String ISOCode;
	private String countryName;
	private String countryCode;
	private String currencySymbol;
	private int backgroundRes = 0;
	//	private MListAdapter mAdapter;

	private TextView tvLabel;
	private ImageView selectorIcon;
	private LinearLayout btnContainer;

	public CountryButton(Context context) {
		super(context);
		init();
	}

	public CountryButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CountryButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CountryButton(Context c, String isoCode, String name, String countryCode, String symbol) {
		super(c);

		//		Log.d(TAG, "NCC - CREATED BUTTON WITH: " + countryCode + "\n" + isoCode + "\n" + name);

		ISOCode = isoCode;
		countryName = name;
		this.countryCode = countryCode;
		currencySymbol = symbol;

		init();
	}

	//	public void setAdapter(ListAdapter adapter) {
	//		mAdapter = adapter;
	//	}

	public void setCustomBackground(int id) {
		backgroundRes = id;
	}

	public void setLabelText(String text) {
		tvLabel.setText(text);
	}

	public void setIsSelected(boolean isSelected) {		
		if(isSelected) {
			selectorIcon.setVisibility(View.VISIBLE);
			Log.d(TAG, "NCC - backgoundRes is: " + backgroundRes);
			if(backgroundRes != 0) {
				btnContainer.setBackgroundColor(Color.parseColor("#33B5E5"));
			}
			else {
				btnContainer.setBackgroundColor(Color.parseColor("#66FF4444"));
			}
		}
		else {
			selectorIcon.setVisibility(View.INVISIBLE);
			btnContainer.setBackgroundColor(Color.parseColor("#88FFFFFF"));
		}

		this.isSelected = isSelected;
	}

	public void setIsFavorited(boolean isFavorited) {
		this.isFavorited = isFavorited;
		Preferences.editPreferences(getContext(), getLabel(), isFavorited);
	}

	public boolean isSelected() {
		return isSelected;
	}

	public boolean isFavorited() {
		return isFavorited;
	}

	public LinearLayout getButtonContainer() {
		return btnContainer;
	}

	private void init() {
		LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(TAG, "NCC - backgoundRes is: " + backgroundRes);
		if(backgroundRes != 0) {
			layoutInflater.inflate(backgroundRes, this);
		}
		else {
			layoutInflater.inflate(R.layout.list_element_layout, this);
		}


		tvLabel = (TextView) findViewById(R.id.tv_label);
		selectorIcon = (ImageView) findViewById(R.id.iv_selected_icon);
		btnContainer = (LinearLayout) findViewById(R.id.ll_button_container);

		//		btnContainer.setOnClickListener(new CountryButtonListener(this));
		tvLabel.setText(countryName);
	}

	//	@Override
	//	public void onClickAction(View v) {
	//		setIsSelected(!isSelected);
	//		
	//		CountryButton btn = (CountryButton) v;
	//		
	////		mAdapter.onClickAction(btn);
	////		mAdapter.notifyDataSetChanged();
	////		mAdapter.notifyDataSetInvalidated();
	//	}



	@Override
	public String getLabel() {
		return countryName;
	}

	@Override
	public String getISOCode() {
		return ISOCode;
	}

	@Override
	public String getCountryCode() {
		return countryCode;
	}

	@Override
	public String getSymbol() {
		//		return currencySymbol;
		return "";
	}

	@Override
	public String toString() {
		return ("BTN: " + countryName + " ISO: " + ISOCode + " CCode: " + countryCode + " SYM: " + getSymbol());
	}
	
}

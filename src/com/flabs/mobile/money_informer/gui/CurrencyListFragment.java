package com.flabs.mobile.money_informer.gui;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Set;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.MSMainActivity;
import com.flabs.mobile.money_informer.model.CountryButton;
import com.flabs.mobile.money_informer.model.CurrencyListAdapter;
import com.flabs.mobile.money_informer.utils.Util;

public class CurrencyListFragment extends ListFragment {

	public static final String TAG = "CurrencyListFragment";

	public static final int LEFT_COL = 0;
	public static final int RIGHT_COL = 1;

	private CountryButton mButton;

	private boolean isLeft = false;
	private boolean isRight = false;

	private ListView listView;
	private ArrayList<CountryButton> countryBtnList;
	private Set<Currency> currencyList;

	public CurrencyListFragment() {

	}

	public CurrencyListFragment(int colId) {
		switch(colId) {
		case LEFT_COL: {
			isLeft = true;
			isRight = false;
			break;
		}
		case RIGHT_COL: {
			isRight = true;
			isLeft = true;
			break;
		}
		default: {
			isRight = false;
			isLeft = true;
		}
		}
	}

	public CurrencyListFragment(int colId, CountryButton btn) {
		mButton = btn;
		switch(colId) {
		case LEFT_COL: {
			isLeft = true;
			isRight = false;
			break;
		}
		case RIGHT_COL: {
			isRight = true;
			isLeft = true;
			break;
		}
		default: {
			isRight = false;
			isLeft = true;
		}
		}

	}

	public boolean isRightColumn() {
		return isRight;
	}

	public boolean isLeftColumn() {
		return isLeft;
	}

	public ListView getListView() {
		return listView;
	}

	public void clearSelected() {
		for (CountryButton btn : countryBtnList) {
			btn.setIsSelected(false);
		}
	}

	public void setButtonSelected(String btnName) {
		clearSelected();
		for(CountryButton btn : countryBtnList) {
			if (btn.getLabel().equalsIgnoreCase(btnName)) {
				btn.setIsSelected(true);
			}
		}
	}

	public CountryButton getSelectedCountry() {
		for(CountryButton btn : countryBtnList) {
			if(btn.isSelected()) {
				return btn;
			}
		}

		return null;
	}

	public ArrayList<CountryButton> getButtonList() {
		return countryBtnList;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		clearSelected();
		countryBtnList.get(position).setIsSelected(true);
		countryBtnList.get(position).getButtonContainer().setBackgroundColor(Color.parseColor("#66FF4444"));
		v.refreshDrawableState();
		setInfoCardData(countryBtnList.get(position));

	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		RelativeLayout llBody = (RelativeLayout) inflater.inflate(R.layout.currency_list_from_layout, container, false);

		listView = (ListView) llBody.findViewById(android.R.id.list);
		setList(listView);
		setListAdapter(listView);
		listView.setSelection(((CurrencyListAdapter) listView.getAdapter()).getSelectedItemPosition(mButton));
		return llBody;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	private void setListAdapter(ListView view) {
		view.setAdapter(new CurrencyListAdapter(getActivity(), 0, countryBtnList));
	}

	private void setList(ListView view) {
		currencyList = Util.getAllCurrencies();
		countryBtnList = Util.getCountyBtnList(getActivity());
		clearSelected();

		for(CountryButton btn : countryBtnList) {
			try {
				if(btn.getLabel().equalsIgnoreCase(mButton.getLabel())) {
					btn.setIsSelected(true);
				}
			} catch(NullPointerException e) {

			}
		}

		setInfoCardData(mButton);
	}

	public void setCountryButtonSelected(CountryButton btn) {
		countryBtnList.get(countryBtnList.indexOf(btn)).setIsSelected(true);
		setInfoCardData(countryBtnList.get(countryBtnList.indexOf(btn)));
	}

	public void setTitleText(String text) {

		if(isRight) {
			((MSMainActivity) getActivity()).setTitleText(CurrencyListFragment.RIGHT_COL, text);
		}
		else {
			((MSMainActivity) getActivity()).setTitleText(CurrencyListFragment.LEFT_COL, text);
		}

	}
	public void setInfoCardData(CountryButton btn) {
		if(isRight) {
			((MSMainActivity) getActivity()).setInfoCardData(CurrencyListFragment.RIGHT_COL, btn);
			this.getListView().refreshDrawableState();
		}
		else {
			((MSMainActivity) getActivity()).setInfoCardData(CurrencyListFragment.LEFT_COL, btn);
			this.getListView().refreshDrawableState();
		}
	}

	public interface CurrencyFragmentListener {
		public void setTitleText(int colId, String text);
		public void setInfoCardData(int colId, CountryButton btn);
		public void setExchangeRate(Double rate);
	}

}

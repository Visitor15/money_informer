package com.flabs.mobile.money_informer.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class CurrencyListAdapter extends ArrayAdapter<CountryButton> {

	private ArrayList<CountryButton> mData = new ArrayList<CountryButton>();

	public CurrencyListAdapter(Context context, int textViewResourceId,
			List<CountryButton> objects) {
		super(context, textViewResourceId, objects);
		mData = (ArrayList<CountryButton>) objects;
	}

	public void clearSelected() {
		for(CountryButton btn : mData) {
			btn.setIsSelected(false);
		}
	}

	public int getSelectedItemPosition(CountryButton btn) {
		int index = 0;

		for(CountryButton mBtn : mData) {
			if(mBtn.getLabel().equalsIgnoreCase(btn.getLabel())) {
				index = mData.indexOf(mBtn);
			}
		}

		return index;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CountryButton btn = (CountryButton) convertView;

		if(btn == null) {
			btn = new CountryButton(parent.getContext());

		}
		btn = mData.get(position);

		return btn;
	}


}

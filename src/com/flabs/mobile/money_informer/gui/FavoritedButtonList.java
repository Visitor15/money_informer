package com.flabs.mobile.money_informer.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.model.CountryButton;
import com.flabs.mobile.money_informer.utils.Util;

public class FavoritedButtonList extends ListFragment {

	public static final String TAG = "FavoritedButtonList";

	private Object[] countryBtnList;
	private ArrayList<CountryButton> favoritesList = new ArrayList<CountryButton>();
	private ListView listView;
	private boolean showAll;

	private ButtonAdapter mAdapter;
	
	public FavoritedButtonList() {
		
	}

	public FavoritedButtonList(boolean fullList) {
		favoritesList.clear();
		showAll = fullList;
		setList();
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		((IFavoritedButtonList) getActivity()).handleOnClick(mAdapter.getItem(position), this);
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

		Log.d(TAG, "NCC - VIEWS: " + container.toString());

		RelativeLayout llBody = (RelativeLayout) inflater.inflate(R.layout.currency_list_from_layout, container, false);

		listView = (ListView) llBody.findViewById(android.R.id.list);
		//		setList(listView);

		mAdapter = new ButtonAdapter(getActivity(), 0, favoritesList);

		setListAdapter(listView);

		return llBody;
	}

	private void setList() {
		countryBtnList = Util.getMasterList(getActivity()).toArray();

		for(int i = 0; i < countryBtnList.length; i++) {
			CountryButton mBtn = (CountryButton) countryBtnList[i];
			if(!showAll) {
				if(mBtn.isFavorited()) {
					mBtn.setBackgroundColor(Color.parseColor("#8833B5E5"));
					favoritesList.add(mBtn);
				}
			}
			else {
				favoritesList.add(mBtn);
			}
		}


		//		for(CountryButton btn : countryBtnList) {
		//			if(!(btn.isFavorited())) {
		//				countryBtnList.remove(btn);
		//			}
		//		}
	}

	private void setListAdapter(ListView view) {
		view.setAdapter(mAdapter);
	}

	public ArrayList<CountryButton> getFavoritesList() {
		return favoritesList;
	}

	public static class ButtonAdapter extends ArrayAdapter<CountryButton> {

		private ArrayList<CountryButton> mData = new ArrayList<CountryButton>();

		public ButtonAdapter(Context context, int textViewResourceId, List<CountryButton> objects) {
			super(context, textViewResourceId, objects);
			mData = (ArrayList<CountryButton>) objects;
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
}

package com.flabs.mobile.money_informer.model;
//package com.flabs.mobile.money.spender.model;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.flabs.mobile.money.spender.gui.BaseFragment;
//import com.flabs.mobile.money.spender.gui.CurrencyListFragment;
//
//
//
//public class MListAdapter extends BaseAdapter implements MListAdapter {
//	public static final String TAG = "ListAdapter";
//
//	private ArrayList<CountryButton> mData = new ArrayList<CountryButton>();
//	private LayoutInflater mInflater;
//	private Context mContext;
//	TextView mTextView;
//
//	private BaseFragment frag;
//
//	public MListAdapter(Context c) {
//		mContext = c;
//		mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	public MListAdapter(Context c, CurrencyListFragment frag) {
//		mContext = c;
//		mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		this.frag = frag;
//	}
//
//	public void addItem(final CountryButton item) {
//		//		item.setOnClickListener(new OnClickListener() {
//		//
//		//			@Override
//		//			public void onClick(View v) {
//		//				onClickAction(v);
//		//			}
//		//			
//		//		});
//		mData.add(item);
//		notifyDataSetChanged();
//	}
//
//	public void clearSelected() {
//		for(CountryButton btn : mData) {
//			btn.setIsSelected(false);
//		}
//	}
//
//	public CountryButton getSelectedBtn() {
//		CountryButton selectedBtn = null;
//		for(CountryButton btn : mData) {
//			if(btn.isSelected()) {
//				selectedBtn = btn;
//			}
//		}
//
//		return selectedBtn;
//	}
//
//	@Override
//	public int getCount() {
//		return mData.size();
//	}
//
//	@Override
//	public CountryButton getItem(int position) {
//		Log.d(TAG, "NCC - getItem: " + "mData: " + mData.get(position));
//		return mData.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup viewGroup) {
//
//		CountryButton cBtn;
//
//		//		if (convertView == null) {
//		//			cBtn = new CountryButton(mContext, "TEST", "TEST", "TEST", "TEST");
//		//		}
//		//		else {
//
//		cBtn = getItem(position);
//		//			cBtn.setAdapter(this);
//		cBtn.setSelected(cBtn.isSelected());
//		//		}
//		if(convertView == null) {
//			convertView = cBtn;
//		}
//
//		Log.d(TAG, "NCC - Get view called with: pos: " + position + " View: " + convertView + " ViewGroup: " + viewGroup);
//		
//		cBtn.setOnClickListener(this);
//
//		return cBtn;
//	}
//
//	//	@Override
//	//	public void onClickAction(View v) {
//	//		CountryButton btn = (CountryButton) v;
//	//		((CurrencyListFragment) frag).setInfoCardData(btn);
//	//	}
//
//	//	@Override
//	//	public void onClick(View v) {
//	//		Log.d(TAG, "NCC - onClick HIT");
//	//		CountryButton btn = (CountryButton) v;
//	//		clearSelected();
//	//		btn.setIsSelected(true);
//	//		notifyDataSetChanged();
//	//		v.refreshDrawableState();
//	//		notifyDataSetChanged();
//	//		((CurrencyListFragment) frag).setInfoCardData(btn);
//	//	}
//
//	public void onClickAction(View v) {
//		Log.d(TAG, "NCC - onClick HIT");
//		CountryButton btn = (CountryButton) v;
//		clearSelected();
//		btn.setIsSelected(true);
//		((CurrencyListFragment) frag).getListView().refreshDrawableState();
//		((CurrencyListFragment) frag).setInfoCardData(btn);
//		this.notifyDataSetChanged();
//	}
//
//
//	public interface CurrencyListAdapter {
//		public void setTitleText(String text);
//		public void setInfoCardData(CountryButton btn);
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		Log.d(TAG, "NCC - onClick HIT");
//		CountryButton btn = (CountryButton) v;
//		clearSelected();
//		btn.setIsSelected(true);
//		((CurrencyListFragment) frag).getListView().refreshDrawableState();
//		((CurrencyListFragment) frag).setInfoCardData(btn);
//		this.notifyDataSetChanged();
//	}
//
//
//
//}

package com.flabs.mobile.money_informer.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.MSMainActivity;
import com.flabs.mobile.money_informer.model.CountryButton;

public class FavoritesDialog extends SherlockFragmentActivity implements IFavoritedButtonList {

	public static final String TAG = "FavoritesDialog";
	
	CurrencyListFragment currencyFrom;
	CurrencyListFragment currencyTo;
	
	RelativeLayout container;
	
	Button btnCancel;
	LinearLayout btnFrom;
	LinearLayout btnTo;
	
	public FavoritesDialog() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.favorites_dialog_layout);
		
		btnCancel = (Button) findViewById(R.id.btn_one);
		btnFrom = (LinearLayout) findViewById(R.id.ll_left_container);
		btnTo = (LinearLayout) findViewById(R.id.ll_right_container);
		
		container = (RelativeLayout) findViewById(R.id.rl_container);
		
		setCancelBtnListener(btnCancel);
		setBtnFromListener(btnFrom);
		setBtnToListener(btnTo);
		
		init();
	}
	
	private void init() {	
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		FavoritedButtonList favList = new FavoritedButtonList(false);
		
		if(favList.getFavoritesList().size() > 0) {
			ft.replace(R.id.rl_container, favList);
			ft.commit();
		}
		else {
			TextView tv = (TextView) findViewById(R.id.tv_nothing_favorited);
			tv.setVisibility(View.VISIBLE);
		}
		
	}
	
	private void setBtnFromListener(LinearLayout btn) {
		btn.setSelected(true);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnFrom.setSelected(true);
				btnTo.setSelected(false);
			}
			
		});
	}
	
	private void setBtnToListener(LinearLayout btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnTo.setSelected(true);
				btnFrom.setSelected(false);
			}
			
		});
	}

	private void setCancelBtnListener(Button btn) {
		btnCancel.setText(getString(R.string.cancel).toUpperCase());
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
			
		});
	}

	@Override
	public void handleOnClick(CountryButton btn, FavoritedButtonList list) {
		Intent mIntent = new Intent();
		Bundle b = new Bundle();
		
		b.putString(MSMainActivity.KEY_NAME, btn.getLabel());
		mIntent.setClass(getApplicationContext(), MSMainActivity.class);
		if(btnFrom.isSelected()) {
			b.putInt(MSMainActivity.KEY_COLUMN_ID, CurrencyListFragment.LEFT_COL);
		}
		else {
			b.putInt(MSMainActivity.KEY_COLUMN_ID, CurrencyListFragment.RIGHT_COL);
		}
		
		mIntent.putExtras(b);
		setResult(MSMainActivity.REQUEST_CODE, mIntent);
		finish();
	}
}

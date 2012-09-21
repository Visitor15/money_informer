package com.flabs.mobile.money_informer.gui;

import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.MSMainActivity;
import com.flabs.mobile.money_informer.model.ChartDownloaderTask;
import com.flabs.mobile.money_informer.model.ExchangeRateDownloaderTask;
import com.flabs.mobile.money_informer.utils.Util;

public class InfoCardFragment extends BaseFragment {

	public static final String SPACE = " ";

	public static final String TAG = "InfoCardFragment";

	private LinearLayout llBody;
	private TextView titleLeft;
	private TextView titleRight;
	private TextView subTitleLeft;
	private TextView subTitleRight;
	private TextView exchangeRate;
	private ImageView chartImage;
	private ImageView timePicker;
	private ImageView leftInfoBtn;
	private ImageView rightInfoBtn;
	private TextView leftDollar;
	private TextView leftChange;
	private TextView rightDollar;
	private TextView rightChange;
	private TextView timeLength;
	private FrameLayout dropShadow;
	private LinearLayout spanningTextContainer;
	private LinearLayout leftColBtn;
	private LinearLayout rightColBtn;
	private ImageView swapBtn;

	private InfoCardFragment mFrag;
	private String countryCode;
	private String currencyCode;
	private String leftCountryName;
	private String rightCountryName;
	private String leftCountryCode;
	private String rightCountryCode;

	public InfoCardFragment() {
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFrag = this;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

		llBody = (LinearLayout) inflater.inflate(R.layout.info_frag_layout, container, false);
		titleLeft = (TextView) llBody.findViewById(R.id.tv_titleLeft);
		subTitleLeft = (TextView) llBody.findViewById(R.id.tv_subtitleLeft);
		titleRight = (TextView) llBody.findViewById(R.id.tv_titleRight);
		subTitleRight = (TextView) llBody.findViewById(R.id.tv_subtitleRight);
//		exchangeRate = (TextView) llBody.findViewById(R.id.tv_exchange_rate);
		leftDollar = (TextView) llBody.findViewById(R.id.tv_left_col_currency);
		leftChange = (TextView) llBody.findViewById(R.id.tv_left_col_currency_change);
		rightDollar = (TextView) llBody.findViewById(R.id.tv_right_col_currency);
		rightChange = (TextView) llBody.findViewById(R.id.tv_right_col_currency_change);
		chartImage = (ImageView) llBody.findViewById(R.id.currency_chart);
		timePicker = (ImageView) llBody.findViewById(R.id.iv_chart_timepicker);
		timeLength = (TextView) llBody.findViewById(R.id.tv_currency_range);
		leftInfoBtn = (ImageView) llBody.findViewById(R.id.iv_left_info_icon);
		rightInfoBtn = (ImageView) llBody.findViewById(R.id.iv_right_info_icon);
		spanningTextContainer = (LinearLayout) llBody.findViewById(R.id.ll_spanning_text_container);
		leftColBtn = (LinearLayout) llBody.findViewById(R.id.ll_left_col_title);
		rightColBtn = (LinearLayout) llBody.findViewById(R.id.ll_right_col_title);
		swapBtn = (ImageView) llBody.findViewById(R.id.iv_swap_icon);

		timeLength.setText(SPACE.concat(getString(R.string.one_year)));

		setTimePickerListener(timePicker);
		setLeftBtnListener(leftColBtn);
		setRightBtnListener(rightColBtn);
		setSwapBtnListener(swapBtn);
//		setLeftInfoBtn(leftInfoBtn);
//		setRightInfoBtn(rightInfoBtn);

		return llBody;
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(leftCountryName != null) {
			subTitleLeft.setText(leftCountryName);
		}
		if(leftCountryCode != null) {
			titleLeft.setText(leftCountryCode);
		}
		if(rightCountryName != null) {
			subTitleRight.setText(rightCountryName);
		}
		if(rightCountryCode != null) {
			titleRight.setText(rightCountryCode);
		}
	}
	
	public LinearLayout getLeftButton() {
		return leftColBtn;
	}
	
	public LinearLayout getRightButton() {
		return rightColBtn;
	}

	public void setTimeLenght(String length) {
		timeLength.setText(SPACE.concat(length));
	}

	public void setTitleLeftText(String text) {
		Log.d(TAG, "NCC - LEFT TITLE TEXT IS: " + text);
		leftCountryCode = text;
		titleLeft.setText(text);
	}

	public void setTitleRightText(String text) {
		rightCountryCode = text;
		titleRight.setText(text);
	}

	public void setSubTitleLeftText(String text) {
		leftCountryName = text;
		subTitleLeft.setText(text);
	}

	public void setSubTitleRightText(String text) {
		rightCountryName = text;
		subTitleRight.setText(text);
	}

//	public void setExchangeRate(String rate) {
//		Log.d(TAG, "NCC - RATE IS: " + rate);
//		exchangeRate.setText(rate);
//	}

	public void setLeftCurrencyText(Double value) {
		DecimalFormat df = new DecimalFormat("00.00");
		String mDouble = df.format(value);
		leftDollar.setText(mDouble.toString().subSequence(0, mDouble.toString().indexOf(".")));
		leftChange.setText(mDouble.toString().substring(mDouble.toString().indexOf(".") + 1, mDouble.toString().length()));
	}

	public void setChartImage(Bitmap image) {
		chartImage.setImageBitmap(image);
	}
	
	public void setCountryCode(String code) {
		countryCode = code;
	}
	
	public void setCurrencyCode(String code) {
		currencyCode = code;
	}
	
	public void setLeftCountryName(String name) {
		leftCountryName = name;
	}
	
	public void setRightCountryName(String name) {
		rightCountryName = name;
	}
	
	public void setLeftCountryCode(String code) {
		leftCountryCode = code;
	}
	
	public void setRightCountryCode(String code) {
		rightCountryCode = code;
	}

	public ImageView getChartView() {
		return chartImage;
	}

	public String getFromIsoCode() {
		return ((MSMainActivity) getActivity()).currencyFrom.getSelectedCountry().getISOCode();
	}

	public String getToIsoCode() {
		return ((MSMainActivity) getActivity()).currencyTo.getSelectedCountry().getISOCode();
	}
	
	public LinearLayout getRootView() {
		return llBody;
	}
	
	public FrameLayout getDropShadow() {
		return dropShadow;
	}
	
	public Drawable getDisplayingChart() {
		return chartImage.getDrawable();
	}
	
	public String getLeftCountryName() {
		return leftCountryName;
	}
	
	public String getRightCountryName() {
		return rightCountryName;
	}

	public void setRightCurrencyText(Double val, Double rate) {

		Double value;

		value = val * rate;

		DecimalFormat df = new DecimalFormat("00.00");

		String mDouble = df.format(value);

		rightDollar.setText(mDouble.toString().subSequence(0, mDouble.toString().indexOf(".")));
		rightChange.setText(mDouble.toString().substring(mDouble.toString().indexOf(".") + 1, mDouble.toString().length()));
	}
	
	public void swapButtons() {
		String tempSubTitle = subTitleLeft.getText().toString();
		String tempTitle = titleLeft.getText().toString();
		
		setTitleLeftText(titleRight.getText().toString());
		setSubTitleLeftText(subTitleRight.getText().toString());
		
		setTitleRightText(tempTitle);
		setSubTitleRightText(tempSubTitle);
		
		String fromISOCode = ((MSMainActivity) getActivity()).getButtonByCountryName(leftCountryName).getISOCode();
		String toISOCode = ((MSMainActivity) getActivity()).getButtonByCountryName(rightCountryName).getISOCode();
		
		ExchangeRateDownloaderTask exchangeTask = new ExchangeRateDownloaderTask(getActivity(), leftCountryCode, rightCountryCode);
		exchangeTask.execute(Util.baseURL
				.concat(fromISOCode)
				.concat(toISOCode)
				.concat("=X"));
	}
	
	private void setSwapBtnListener(ImageView btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				swapButtons();
			}
			
		});
	}
	
	private void setLeftBtnListener(LinearLayout v) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				InfoSelectorDialog infoDialog = new InfoSelectorDialog(((MSMainActivity) getActivity()).getButtonByCountryName(leftCountryName));
				infoDialog.show(getActivity().getSupportFragmentManager(), TAG);
			}
			
		});
	}
	
	private void setRightBtnListener(LinearLayout v) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				InfoSelectorDialog infoDialog = new InfoSelectorDialog(((MSMainActivity) getActivity()).getButtonByCountryName(rightCountryName));
				infoDialog.show(getActivity().getSupportFragmentManager(), TAG);
			}
			
		});
	}

	private void setTimePickerListener(ImageView view) {
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TimeSpanChooserDialog dialog = new TimeSpanChooserDialog(mFrag);
				dialog.show(getActivity().getSupportFragmentManager(), TAG);
			}

		});
	}
	
	public void showFullScreenChart() {
	}

	protected void updateUrlAndImage(String mUrl, String timeLength) {
		setTimeLenght(timeLength);
		ChartDownloaderTask task = new ChartDownloaderTask(getChartView(), mUrl);
		task.execute();
	}
}

package com.flabs.mobile.money_informer.gui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.model.Preferences;
import com.flabs.mobile.money_informer.utils.Util;

public class TimeSpanChooserDialog extends SherlockDialogFragment {
	
	public static final String TAG = "TimeSpanChooserDialog";
	
	private InfoCardFragment mFrag;
	
	private Button btnOkay;
	private Button btnCancel;
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
	private CheckBox savePref;
	
	private ArrayList<TextView> textViewList = new ArrayList<TextView>();
	private ArrayList<LinearLayout> linearLayoutList = new ArrayList<LinearLayout>();
	
	public TimeSpanChooserDialog() {
	}
	
	public TimeSpanChooserDialog(InfoCardFragment fragment) {
		mFrag = fragment;
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
	        View v = inflater.inflate(R.layout.time_chooser_dialog_layout, container, false);
	        
	        btnOkay = (Button) v.findViewById(R.id.btn_one);
	        btnCancel = (Button) v.findViewById(R.id.btn_two);
	        
	        llOneDay = (LinearLayout) v.findViewById(R.id.ll_one_day);
	        tvOneDay = (TextView) llOneDay.findViewById(R.id.tv_text);
	        llFiveDays = (LinearLayout) v.findViewById(R.id.ll_five_days);
	        tvFiveDays = (TextView) llFiveDays.findViewById(R.id.tv_text);
	        llOneMonth = (LinearLayout) v.findViewById(R.id.ll_one_month);
	        tvOneMonth = (TextView) llOneMonth.findViewById(R.id.tv_text);
	        llThreeMonths = (LinearLayout) v.findViewById(R.id.ll_three_months);
	        tvThreeMonths = (TextView) llThreeMonths.findViewById(R.id.tv_text);
	        llSixMonths = (LinearLayout) v.findViewById(R.id.ll_six_months);
	        tvSixMonths = (TextView) llSixMonths.findViewById(R.id.tv_text);
	        llOneYear = (LinearLayout) v.findViewById(R.id.ll_one_year);
	        tvOneYear = (TextView) llOneYear.findViewById(R.id.tv_text);
	        llTwoYears = (LinearLayout) v.findViewById(R.id.ll_two_years);
	        tvTwoYears = (TextView) llTwoYears.findViewById(R.id.tv_text);
	        llFiveYears = (LinearLayout) v.findViewById(R.id.ll_five_years);
	        tvFiveYears = (TextView) llFiveYears.findViewById(R.id.tv_text);
	        savePref = (CheckBox) v.findViewById(R.id.cb_save_graph_time_pref);
	        
	        linearLayoutList.add(llOneDay);
	        linearLayoutList.add(llFiveDays);
	        linearLayoutList.add(llOneMonth);
	        linearLayoutList.add(llThreeMonths);
	        linearLayoutList.add(llSixMonths);
	        linearLayoutList.add(llOneYear);
	        linearLayoutList.add(llTwoYears);
	        linearLayoutList.add(llFiveYears);
	        
	        setButtonTexts();
	        
	        btnOkay.setText("Ok");
	        btnCancel.setText("Cancel");
	        
	        setCancelBtnListener(btnCancel);
	        setOkayBtnListener(btnOkay);
	        setButtonListeners();
	        
	        return v;
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
	 
	 private void setOkayBtnListener(Button btn) {
		 btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] params = getParamsSelected();
				String mUrl = Util.getChartUrl(mFrag.getFromIsoCode(), mFrag.getToIsoCode(), params[0], params[1]);
				mFrag.updateUrlAndImage(mUrl, params[2]);
				
				if(savePref.isChecked()) {
					Preferences.editPreferences(getActivity(), Preferences.KEY_GRAPH_TIME_FRAME, params[0].concat(params[1]));
				}
				
				getDialog().dismiss();
			}
			 
		 });
	 }
	
	 private void setCancelBtnListener(Button btn) {
		 btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
			 
		 });
	 }
}

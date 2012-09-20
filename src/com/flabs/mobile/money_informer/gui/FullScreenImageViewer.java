package com.flabs.mobile.money_informer.gui;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.flabs.mobile.money.spender.R;

public class FullScreenImageViewer extends SherlockDialogFragment {

	public static final String TAG = "FullScreenImageViewer";
	
	private RelativeLayout parent;
	private ImageView image;
	private Drawable chart;
	
	public FullScreenImageViewer(Drawable image) {
		chart = image;
	}
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog);
		setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

		parent = (RelativeLayout) inflater.inflate(R.layout.fullscreen_image_viewer_layout, container, false);
		image = (ImageView) parent.findViewById(R.id.iv_image);
		image.setImageDrawable(chart);
		
		return parent;
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    getDialog().dismiss();
	}
	
	
	public void setImage(Drawable d) {
		image.setImageDrawable(d);
	}
}

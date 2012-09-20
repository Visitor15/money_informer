package com.flabs.mobile.money_informer.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flabs.mobile.money.spender.R;

public class ChartDownloaderTask extends AsyncTask<Void, Void, Void> {

	public static final String TAG = "ChartDownloaderTask";

	ViewGroup parent;
	Bitmap bitmap;
	ImageView i;
	RelativeLayout progBar;
	LinearLayout panel;
	String mUrl;

	public ChartDownloaderTask(ImageView view, String url) {
		i = view;
		mUrl = url;
	}
	
	//This onPreExecute method could use a little cleaning up.
	@Override
	protected void onPreExecute() {
		Log.d(TAG, "NCC - onPreExecute");
		parent = (ViewGroup) i.getParent();
		progBar = (RelativeLayout) ((ViewGroup) parent.getParent().getParent()).findViewById(R.id.progress_circle);
		panel = (LinearLayout) ((ViewGroup) parent.getParent()).findViewById(R.id.data_field);
		progBar.setVisibility(View.VISIBLE);
		((LinearLayout) ((ViewGroup) i.getParent()).findViewById(R.id.ll_spanning_text_container)).setVisibility(View.GONE);
		i.setVisibility(View.GONE);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			Log.d(TAG, "NCC - doInBackground with URL: " + mUrl);
			bitmap = BitmapFactory.decodeStream((InputStream)new URL(mUrl).getContent());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		Log.d(TAG, "NCC - onPostExecute");
		progBar.setVisibility(View.INVISIBLE);
		i.setImageBitmap(bitmap);
		
//		((ViewGroup) i.getParent().getParent()).setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
		i.setVisibility(View.VISIBLE);
		((LinearLayout) ((ViewGroup) i.getParent()).findViewById(R.id.ll_spanning_text_container)).setVisibility(View.VISIBLE);
	}
}

package com.flabs.mobile.money_informer.model;

import java.util.ArrayList;

import org.apache.commons.lang3.ObjectUtils.Null;

import android.os.AsyncTask;
import android.view.View;

public class TutorialFlasherTask extends AsyncTask<Null, Null, Null> {

	public static boolean isRunning = true;

	private ArrayList<View> mList;

	public TutorialFlasherTask(ArrayList<View> viewList) {
		mList = viewList;
	}

	@Override
	protected Null doInBackground(Null... params) {
		while(isRunning) {
			for(final View v : mList) {
				v.post(new Runnable() {

					@Override
					public void run() {
						v.setSelected(true);
					}

				});

				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				v.post(new Runnable() {

					@Override
					public void run() {
						v.setSelected(false);
					}
				});

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return null;
	}
}

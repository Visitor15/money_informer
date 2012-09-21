package com.flabs.mobile.money_informer.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class ExchangeRateDownloaderTask extends AsyncTask<String, Void, String> {
	
	public static final String TAG = "ExchangeRateDownloaderTask";
	
	Activity mActivity;
	
	String mRate = "";
	String countryISOFrom = "";
	String countryISOTo = "";

	Double rate = 0.0;
	
	boolean showSelectedBtn;

	public ExchangeRateDownloaderTask(String countryISOFrom,
			String countryISOTo) {
		this.countryISOFrom = countryISOFrom;
		this.countryISOTo = countryISOTo;
	}
	
	public ExchangeRateDownloaderTask(Activity activity, String countryISOFrom, String CountryISOTo, boolean showSelectedButtonInList) {
		mActivity = activity;
		this.countryISOFrom = countryISOFrom;
		this.countryISOTo = countryISOTo;
		showSelectedBtn = showSelectedButtonInList;
	}
	
	@Override
	protected void onPreExecute() {
		((IExchangeRateTask) mActivity).updateUI(rate, true, false, showSelectedBtn);
	}

	@Override
	protected String doInBackground(String... args) {
		Log.d(TAG, "NCC - URL is: " + args[0]);
		String line = "";
		try {
			BufferedInputStream inputStream = new BufferedInputStream(new URL(args[0]).openStream());
			BufferedOutputStream bout = new BufferedOutputStream(null, 1024);
			byte data[] = new byte[1024];

			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, Charset.defaultCharset());


			while(inputStream.read(data) != 0) {

				line = writer.toString();

				StringTokenizer token = new StringTokenizer(line, ",");
				while(token.hasMoreTokens()) {
					try {
						rate = Double.parseDouble(token.nextToken());
						mRate = Double.toString(rate);

						break;
					} catch(NumberFormatException e) {

					}
				}
				break;
			}

			//			bout.close();
			//			fos.close();
			inputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Log.d(TAG, "NCC - URL : Returning rate " + mRate);

		return mRate;
	}

	@Override
	protected void onPostExecute(String result) {
		rate = Double.parseDouble(result);
		((IExchangeRateTask) mActivity).updateUI(rate, false, false, showSelectedBtn);
		
		
	}

	//    public void getExchangeRate(String countryISOFrom, String countryISOTo) {
	//        DownloadExchangeRateTask task = new DownloadExchangeRateTask(countryISOFrom, countryISOTo);
	//        task.execute(baseURL.concat(countryISOFrom).concat(countryISOTo).concat("=X"));
	//
	//      }
}
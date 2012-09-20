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

public class ExchangeRateDownloaderTask extends AsyncTask<String, Void, String> {
	
	Activity mActivity;
	
	String mRate = "";
	String countryISOFrom = "";
	String countryISOTo = "";

	Double rate = 0.0;

	public ExchangeRateDownloaderTask(String countryISOFrom,
			String countryISOTo) {
		this.countryISOFrom = countryISOFrom;
		this.countryISOTo = countryISOTo;
	}
	
	public ExchangeRateDownloaderTask(Activity activity, String countryISOFrom, String CountryISOTo) {
		mActivity = activity;
		this.countryISOFrom = countryISOFrom;
		this.countryISOTo = countryISOTo;
	}
	
	@Override
	protected void onPreExecute() {
		((IExchangeRateTask) mActivity).updateUI(rate, true, false);
	}

	@Override
	protected String doInBackground(String... args) {

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

		return mRate;
	}

	@Override
	protected void onPostExecute(String result) {
		rate = Double.parseDouble(result);
		((IExchangeRateTask) mActivity).updateUI(rate, false, false);
		
		
	}

	//    public void getExchangeRate(String countryISOFrom, String countryISOTo) {
	//        DownloadExchangeRateTask task = new DownloadExchangeRateTask(countryISOFrom, countryISOTo);
	//        task.execute(baseURL.concat(countryISOFrom).concat(countryISOTo).concat("=X"));
	//
	//      }
}
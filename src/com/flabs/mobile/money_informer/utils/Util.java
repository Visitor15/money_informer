package com.flabs.mobile.money_informer.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;

import com.flabs.mobile.money.spender.R;
import com.flabs.mobile.money_informer.model.CountryButton;
import com.flabs.mobile.money_informer.model.Preferences;

public class Util {

	public static final String TAG = "Util";

	public static boolean showAds = true;

	public static final String KEY_PACKAGE = "com.flabs.mobile.money_informer.key";
	public static final String baseURL = "http://finance.yahoo.com/d/quotes.csv?e=.csv&f=sl1d1t1&s=";
	private static final String baseChartURL = "http://chart.finance.yahoo.com/z?s=";
	private static final String endChartURL = "&q=&l=&z=l&a=v&p=s&lang=en-US&region=US";
	private static final String middleChartURL = "%3dX&t=";


	private static final String fileName = "quotes.csv";
	private static ArrayList<CountryButton> masterBtnList = new ArrayList<CountryButton>();
	private static HashMap<String, String> currencyTable = new HashMap<String, String>();
	static double rate = 0.0;
	static boolean isFinished;

	public static Set<Currency> getAllCurrencies()
	{
		Set<Currency> toret = new HashSet<Currency>();
		Locale[] locs = Locale.getAvailableLocales();

		for(Locale loc : locs) {
			try {
				toret.add( Currency.getInstance( loc ) );
			} catch(Exception exc)
			{
				// Locale not found
			}
		}

		return toret;
	}

	public static boolean shouldNotShowAds(Context c) {
		boolean val = hasKeyInstalled(c);
		Log.d(TAG, "NCC - Returning: " + val);
		return val;
	}

	public static void setShouldShowAds(boolean val) {
		showAds = val;
	}

	public static boolean hasKeyInstalled(Context c) {
		boolean hasKeyInstalled = false;

		final PackageManager pkgMgr = c.getPackageManager();
		String string = KEY_PACKAGE;

		try {
			pkgMgr.getPackageInfo(string, 0);
			final int sigMatch = pkgMgr.checkSignatures(c.getPackageName(), string);
			hasKeyInstalled = (sigMatch == PackageManager.SIGNATURE_MATCH);
		} catch (NameNotFoundException e) {
			setShouldShowAds(true);
			return false;
		}

		setShouldShowAds(!hasKeyInstalled);
		return hasKeyInstalled;
	}

	private static void createMasterList(ArrayList<CountryButton> list, Context c) {
		Currency currency;
		String iso = "";

		String[] isoCountries = Locale.getISOCountries();
		for (String country : isoCountries) {
			Locale locale = new Locale("en", country);

			//Currency.getInstance() can return null here.
			currency = Currency.getInstance(locale);

			try {
				iso	= currency.getCurrencyCode();
				String code = locale.getISO3Country();
				String name = locale.getDisplayCountry();
				String symbol = currencyTable.get(code);

				if (!"".equals(iso) && !"".equals(code)
						&& !"".equals(name)) {
					CountryButton btn = new CountryButton(c, iso, name, code, symbol);	
					list.add(btn);
					//					if(Preferences.getPreference(c, btn.getLabel(), true) != null) {
					//						btn.setIsFavorited((Boolean) Preferences.getPreference(c, btn.getLabel(), true));

					try {
						btn.setIsFavorited((Boolean) Preferences.getPreference(c, btn.getLabel(), true));
					} catch(NullPointerException e) {
						btn.setIsFavorited(false);
					}

					//					}
					//					else {
					//						btn.setIsFavorited(false);
					//					}
				}
			} catch(NullPointerException e) {

			}

		}
	}

	public static void clearFavoritesForMasterList(Context c) {
		ArrayList<CountryButton> list = getMasterList(c);

		for(CountryButton mBtn : list) {
			mBtn.setIsFavorited(false);
			Preferences.editPreferences(c, mBtn.getLabel(), true);
		}
	}

	public static CountryButton getCountryButtonByNameFromMasterList(Context c, String name) {
		CountryButton btn = null;

		ArrayList<CountryButton> list = getMasterList(c);

		for(CountryButton mBtn : list) {
			if(mBtn.getLabel().equalsIgnoreCase(name)) {
				btn = mBtn;
				break;
			}
			else {
				continue;
			}
		}

		return btn;
	}

	public static ArrayList<CountryButton> getMasterList(Context c) {
		if(masterBtnList.size() == 0) {
			createMasterList(masterBtnList, c);
		}

		Collections.sort(masterBtnList, new Comparator<CountryButton>() {

			@Override
			public int compare(CountryButton c1, CountryButton c2) {
				return c1.getLabel().compareTo(c2.getLabel());
			}

		});

		return masterBtnList;
	}

	public static ArrayList<CountryButton> getCountyBtnList(Context c) {

		if(masterBtnList.size() == 0) {
			Log.d(TAG, "NCC - Creating master list");
			createMasterList(masterBtnList, c);
		}

		buildCurrencyTable(currencyTable);

		Currency currency;
		String iso = "";


		ArrayList<CountryButton> btnList = new ArrayList<CountryButton>();
		String[] isoCountries = Locale.getISOCountries();
		for (String country : isoCountries) {
			Locale locale = new Locale("en", country);

			//Currency.getInstance() can return null here.
			currency = Currency.getInstance(locale);

			try {
				iso	= currency.getCurrencyCode();
				String code = locale.getISO3Country();
				String name = locale.getDisplayCountry();
				String symbol = currencyTable.get(code);

				if (!"".equals(iso) && !"".equals(code)
						&& !"".equals(name)) {
					btnList.add(new CountryButton(c, iso, name, code, symbol));
				}
			} catch(NullPointerException e) {

			}

		}

		Collections.sort(btnList, new Comparator<CountryButton>() {

			@Override
			public int compare(CountryButton c1, CountryButton c2) {
				return c1.getLabel().compareTo(c2.getLabel());
			}

		});

		return btnList;
	}

	public String getCurrencySymbol(String countryCode) {
		String symbol = "";

		symbol = currencyTable.get(countryCode);

		return symbol;
	}

	private static void buildCurrencyTable(HashMap<String, String> list) {
		String[] isoCountries = Locale.getISOCountries();

		for(String s : isoCountries) {
			Locale locale = new Locale("en", s);
			String iso = locale.getISO3Country();
			list.put(iso, "$");
		}
	}

	public static void updateCurrencyTable(String isoCode, String currencySymbol) {
		if(currencyTable != null) {
			currencyTable.put(isoCode, currencySymbol);
		}
	}

	//	public static double getExchangeRate(String countryISOFrom, String countryISOTo) {
	//		
	//		
	//        DownloadExchangeRateTask task = new DownloadExchangeRateTask(countryISOFrom, countryISOTo);
	//        task.execute(baseURL.concat(countryISOFrom).concat(countryISOTo).concat("=X"));
	//	
	////        while(!isFinished) {
	////        	try {
	////        		Log.d(TAG, "NCC - isFinished is" + Boolean.toString(isFinished));
	////				Thread.sleep(100);
	////			} catch (InterruptedException e) {
	////				// TODO Auto-generated catch block
	////				e.printStackTrace();
	////			}
	////        }
	//        
	//        
	//        Log.d(TAG, "NCC - Returning: " + rate);
	//		return rate;
	//	}

	public static String getChartUrl(String countryIsoFrom, String countryIsoTo, String realTimeSpan) {
		String chartUrl = "";

		chartUrl = baseChartURL.concat(countryIsoFrom).concat(countryIsoTo).concat(middleChartURL).concat(realTimeSpan).concat(endChartURL);

		Log.d(TAG, "NCC - Returning chartURL: " + chartUrl);

		return chartUrl;
	}

	public static String getChartUrl(String countryIsoFrom, String countryIsoTo, String timeSpanInt, String timeSpanType) {
		String chartUrl = "";

		chartUrl = baseChartURL.concat(countryIsoFrom).concat(countryIsoTo).concat(middleChartURL).concat(timeSpanInt).concat(timeSpanType).concat(endChartURL);

		Log.d(TAG, "NCC - Returning chartURL: " + chartUrl);

		return chartUrl;
	}

	public static String getTimeLengthString(Context c) {
		String prefVal = (String) Preferences.getPreference(c, Preferences.KEY_GRAPH_TIME_FRAME, false);
		String returnVal = "";

		if(prefVal.equalsIgnoreCase("1d")) {
			returnVal = c.getString(R.string.one_day);
		}
		else if(prefVal.equalsIgnoreCase("5d")) {
			returnVal = c.getString(R.string.five_days);
		}
		else if(prefVal.equalsIgnoreCase("1w")) {
			returnVal = c.getString(R.string.one_week);
		}
		else if(prefVal.equalsIgnoreCase("10w")) {
			returnVal = c.getString(R.string.ten_weeks);
		}
		else if(prefVal.equalsIgnoreCase("1m")) {
			returnVal = c.getString(R.string.one_month);
		}
		else if(prefVal.equalsIgnoreCase("3m")) {
			returnVal = c.getString(R.string.three_months);
		}
		else if(prefVal.equalsIgnoreCase("6m")) {
			returnVal = c.getString(R.string.six_months);
		}
		else if(prefVal.equalsIgnoreCase("1y")) {
			returnVal = c.getString(R.string.one_year);
		}
		else if(prefVal.equalsIgnoreCase("2y")) {
			returnVal = c.getString(R.string.two_years);
		}
		else if(prefVal.equalsIgnoreCase("5y")) {
			returnVal = c.getString(R.string.five_years);
		}

		return returnVal;
	}

	public static float dipToPixels(int numOfPixels, Context c) {
		float val = 0f;

		val = numOfPixels * c.getResources().getDisplayMetrics().density;

		return val;
	}
	
	public static void sendToMarket(Context c) {
		try {
		    c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.flabs.mobile.money_informer.key")));
		} catch (android.content.ActivityNotFoundException anfe) {
		    c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.flabs.mobile.money_informer.key")));
		}
	}

	//	private static class DownloadExchangeRateTask extends AsyncTask<String, Void, String> {
	//		String mRate = "";
	//		String countryISOFrom = "";
	//		String countryISOTo = "";
	//
	//		public DownloadExchangeRateTask(String countryISOFrom,
	//				String countryISOTo) {
	//			this.countryISOFrom = countryISOFrom;
	//			this.countryISOTo = countryISOTo;
	//			}
	//
	//		@Override
	//		protected String doInBackground(String... args) {
	//			
	//			isFinished = false;
	//			
	//			String line = "";
	//			try {
	//				BufferedInputStream inputStream = new BufferedInputStream(new URL(args[0]).openStream());
	////				FileOutputStream fos = new FileOutputStream(fileName);
	//				BufferedOutputStream bout = new BufferedOutputStream(null, 1024);
	//				byte data[] = new byte[1024];
	//				
	//				Log.d(TAG, "NCC - in the try block");
	//				
	//				StringWriter writer = new StringWriter();
	//				IOUtils.copy(inputStream, writer, Charset.defaultCharset());
	//				
	//				
	//				while(inputStream.read(data) != 0) {
	//					
	//					line = writer.toString();
	//					
	//					Log.d(TAG, "NCC - line is: " + line);
	//					StringTokenizer token = new StringTokenizer(line, ",");
	//					while(token.hasMoreTokens()) {
	//						try {
	//							rate = Double.parseDouble(token.nextToken());
	//							Log.d(TAG, "NCC - RATE IS: " + rate);
	//							mRate = Double.toString(rate);
	//							
	//							break;
	//						} catch(NumberFormatException e) {
	//							
	//						}
	//					}
	//					break;
	//				}
	//				
	////				bout.close();
	////				fos.close();
	//				inputStream.close();
	//			} catch (MalformedURLException e) {
	//				Log.d(TAG, "NCC - CAUGHT MalformedURL");
	//				e.printStackTrace();
	//			} catch (IOException e) {
	//				Log.d(TAG, "NCC - CAUGHT IOException");
	//				e.printStackTrace();
	//			}
	//			
	//			return mRate;
	//		}
	//		
	//	    @Override
	//	    protected void onPostExecute(String result) {
	//	    	Log.d(TAG, "NCC - RESULT IS: " + result);
	//	    	rate = Double.parseDouble(result);
	//	    	Log.d(TAG, "NCC - Setting isFinished to true");
	//	    	isFinished = true;
	//	    }
	//	    
	////	    public void getExchangeRate(String countryISOFrom, String countryISOTo) {
	////	        DownloadExchangeRateTask task = new DownloadExchangeRateTask(countryISOFrom, countryISOTo);
	////	        task.execute(baseURL.concat(countryISOFrom).concat(countryISOTo).concat("=X"));
	////
	////	      }
	//	}
}

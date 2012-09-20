package com.flabs.mobile.money_informer.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preferences{

	public static final String TAG = "Preferences";

	public static final String KEY_FIRST_BOOT = "first_boot";
	public static final String KEY_HOME_LOCATION = "home_location";
	public static final String KEY_HOME_LOCATION_LABEL = "home_location_label";
	public static final String KEY_TRAVELING_LOCATION = "traveling_location";
	public static final String KEY_GRAPH_TIME_FRAME = "graph_time_frame";
	public static final String KEY_GOOGLE_LINK_CURRENCY = "google_link_currency";
	public static final String KEY_GOOGLE_LINK_COUNTRY = "google_link_country";
	public static final String KEY_WIKIPEDIA_LINK_CURRENCY = "wikipedia_link_currency";
	public static final String KEY_WIKIPEDIA_LINK_COUNTRY = "wikipedia_link_country";
	public static final String KEY_EULA_ACCEPT_KEY = "eula_accept_key";
	public static final String KEY_ACCEPTED_ADS = "accepted_ads";
	public static final String KEY_DEFAULT_HOME_COUNTRY = "United States";
	public static final String KEY_DEFAULT_TRAVELING_COUNTRY = "Japan";

	private static void setDefaultPreferences(Context c) {
		Log.d(TAG, "NCC - Setting defaults");
		editPreferences(c, KEY_HOME_LOCATION, KEY_DEFAULT_HOME_COUNTRY);
		editPreferences(c, KEY_TRAVELING_LOCATION, KEY_DEFAULT_TRAVELING_COUNTRY);
		editPreferences(c, KEY_DEFAULT_HOME_COUNTRY, true);
		editPreferences(c, KEY_DEFAULT_TRAVELING_COUNTRY, true);
		editPreferences(c, KEY_GRAPH_TIME_FRAME, "1y");
		editPreferences(c, KEY_GOOGLE_LINK_CURRENCY, false);
		editPreferences(c, KEY_GOOGLE_LINK_COUNTRY, true);
		editPreferences(c, KEY_WIKIPEDIA_LINK_CURRENCY, false);
		editPreferences(c, KEY_WIKIPEDIA_LINK_COUNTRY, true);
		editPreferences(c, KEY_EULA_ACCEPT_KEY, false);
		editPreferences(c, KEY_ACCEPTED_ADS, false);
		editPreferences(c, KEY_FIRST_BOOT, true);
	}


	public static SharedPreferences getSharedPreferences(Context c) {
		return c.getSharedPreferences(TAG, 0);
	}

	public static boolean editPreferences(Context c, String key, Object obj) {
		SharedPreferences mPrefs = c.getSharedPreferences(TAG, 0);

		if(obj instanceof Integer) {
			mPrefs.edit().putInt(key, (Integer) obj).commit();
			return true;
		}
		else if(obj instanceof Long) {
			mPrefs.edit().putLong(key, (Long) obj).commit();
			return true;
		}
		else if(obj instanceof Boolean) {
			mPrefs.edit().putBoolean(key, (Boolean) obj).commit();
			return true;
		}
		else if(obj instanceof String) {
			mPrefs.edit().putString(key, (String) obj).commit();
			return true;
		}
		throw new IllegalArgumentException();	
	}

	public static Object getPreference(Context c, String key, boolean isButton) {
		if(!isButton){
			if(c.getSharedPreferences(TAG, 0).getAll().get(key) == null) {
				setDefaultPreferences(c);
			}
			return c.getSharedPreferences(TAG, 0).getAll().get(key);
		}
		else {
			return c.getSharedPreferences(TAG, 0).getAll().get(key);
		}
	}
}

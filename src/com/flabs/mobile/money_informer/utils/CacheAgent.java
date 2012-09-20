package com.flabs.mobile.money_informer.utils;

import java.util.HashMap;

//Singleton Class that manages separate HashMaps for individual exchange rates and
//downloaded exchange charts.

public class CacheAgent {

	public static final String TAG = "CacheAgent";
	
	private static CacheAgent instance = null;
	
	private HashMap<String, Double> exchangeRateMap = new HashMap<String, Double>();
	
	private CacheAgent() {
		instance = new CacheAgent();
	}
	
	public static CacheAgent getInstance() {
		if(instance == null) {
			new CacheAgent();
		}
		
		return instance;
	}
	
}

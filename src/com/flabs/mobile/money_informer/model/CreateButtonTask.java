package com.flabs.mobile.money_informer.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.os.AsyncTask;

public class CreateButtonTask extends AsyncTask<Void, Void, Void> {

	Context c;
	private HashMap<String, String> currencyTable;
	private ArrayList<CountryButton> list;
	
	public CreateButtonTask(Context c, ArrayList<CountryButton> list, HashMap<String, String> currencyTable) {
		this.c = c;
		this.list = list;
		this.currencyTable = currencyTable;
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		

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
					if(Preferences.getPreference(c, btn.getLabel(), true) != null) {
						btn.setIsFavorited((Boolean) Preferences.getPreference(c, btn.getLabel(), true));
					}
					else {
						btn.setIsFavorited(false);
					}
				}
			} catch(NullPointerException e) {

			}

		}
		
		
		return null;
	}

}

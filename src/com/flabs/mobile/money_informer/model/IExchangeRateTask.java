package com.flabs.mobile.money_informer.model;

public interface IExchangeRateTask {

	public void updateUI(Double rate, boolean isPreExecute, boolean shouldBlink);
}

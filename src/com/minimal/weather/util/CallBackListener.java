package com.minimal.weather.util;

public interface CallBackListener {
	void onFinished(String response);

	void onError(Exception e);
}

package com.minimal.weather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
	}
}

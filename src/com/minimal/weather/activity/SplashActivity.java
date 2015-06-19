package com.minimal.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import com.minimal.weather.R;
import com.minimal.weather.util.XmlUtil;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		SharedPreferences pref = getSharedPreferences("minimalweather",
				Context.MODE_PRIVATE);
		if (!pref.getBoolean("isInitData", false)) {
			XmlUtil.parseXml2Save(SplashActivity.this, R.xml.citys_weather);
			Editor editor = pref.edit();
			editor.putBoolean("isInitData", true);
			editor.commit();
		}
		Intent intent = new Intent(SplashActivity.this,
				ChooseAreaActivity.class);
		startActivity(intent);

	}
}

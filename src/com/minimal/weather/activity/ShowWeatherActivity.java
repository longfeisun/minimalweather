package com.minimal.weather.activity;

import com.minimal.weather.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class ShowWeatherActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_show);
		
		String districtCode = getIntent().getStringExtra("districtCode");
		if(!TextUtils.isEmpty(districtCode)){
			
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * 
	 * @param context 上下文对象
	 * @param districtCode 区code
	 */
	public static void actionStart(Context context, String districtCode){
		Intent intent = new Intent(context, ShowWeatherActivity.class);
		intent.putExtra("districtCode", districtCode);
		context.startActivity(intent);
	}
	
}

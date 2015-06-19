package com.minimal.weather.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.minimal.weather.db.MinimalWeatherDB;
import com.minimal.weather.entity.City;
import com.minimal.weather.entity.District;
import com.minimal.weather.entity.Province;

public class Utility {

	/**
	 * 
	 * 
	 * @param response
	 * @return
	 */
	public static boolean handleProvincesResponse(MinimalWeatherDB db,
			String response) {
		// request: http://www.weather.com.cn/data/list3/city.xml
		// response:
		boolean result = false;
		if (!TextUtils.isEmpty(response)) {
			String[] arr = response.split(",");
			if (arr != null && arr.length > 0) {
				for (String str : arr) {
					String[] strs = str.split("\\|");
					Province province = new Province();
					province.setProvinceCode(strs[0]);
					province.setProvinceName(strs[1]);
					db.insertProvince(province);
				}
				result = true;
			}
		}
		return result;
	}

	public static boolean handleCitiesResponse(MinimalWeatherDB db,
			String response, String provinceCode) {
		boolean result = false;
		if (!TextUtils.isEmpty(response)) {
			String[] arr = response.split(",");
			if (arr != null && arr.length > 0) {
				for (String str : arr) {
					String[] strs = str.split("\\|");
					City city = new City();
					city.setCityCode(strs[0]);
					city.setCityName(strs[1]);
					city.setProvinceCode(provinceCode);
					db.insertCity(city);
				}
				result = true;
			}
		}
		return result;
	}

	public static boolean handleCountiesResponse(MinimalWeatherDB db,
			String response, String cityCode) {
		boolean result = false;

		if (!TextUtils.isEmpty(response)) {
			String[] arr = response.split(",");
			if (arr != null && arr.length > 0) {
				for (String str : arr) {
					String[] strs = str.split("\\|");
					District district = new District();
					district.setDistrictCode(strs[0]);
					district.setDistrictName(strs[1]);
					district.setCityCode(cityCode);
					db.insertDistrict(district);
				}
				result = true;
			}
		}
		return result;
	}

	/**
	 * {"weatherinfo":
	 * {"city":"昆山","cityid":"101190404","temp1":"21℃","temp2":"9℃",
	 * "weather":"多云转小雨","img1":"d1.gif","img2":"n7.gif","ptime":"11:00"} }
	 * 
	 * @param context
	 * @param response
	 */
	public static void handleWeatherResponse(Context context, String response) {
		if (!TextUtils.isEmpty(response)) {
			try {
				JSONObject obj = new JSONObject(response);
				JSONObject weatherObj = obj.getJSONObject("weatherinfo");
				String cityName = weatherObj.getString("city");
				String weatherCode = weatherObj.getString("cityid");
				String temp1 = weatherObj.getString("temp1");
				String temp2 = weatherObj.getString("temp2");
				String weather = weatherObj.getString("weather");
				String img1 = weatherObj.getString("img1");
				String img2 = weatherObj.getString("img2");
				String ptime = weatherObj.getString("ptime");
				saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
						weather, img1, img2, ptime);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weather,
			String img1, String img2, String ptime) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putString("cityName", cityName);
		editor.putString("weatherCode", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather", weather);
		editor.putString("img1", img1);
		editor.putString("img2", img2);
		editor.putString("ptime", ptime);
		editor.putString("currenttime", sdf.format(new Date()));
		editor.commit();
	}

}

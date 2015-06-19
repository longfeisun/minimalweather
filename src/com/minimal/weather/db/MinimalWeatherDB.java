package com.minimal.weather.db;

import java.util.ArrayList;
import java.util.List;

import com.minimal.weather.entity.City;
import com.minimal.weather.entity.District;
import com.minimal.weather.entity.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MinimalWeatherDB {

	private static final String DB_NAME = "minimalweather.db";
	private static final int DB_VERSION = 1;
	private static MinimalWeatherDB minimalWeatherDB;
	private SQLiteDatabase db;

	/**
	 * 构造方法中初始化成员变量
	 * 
	 * @param context
	 */
	private MinimalWeatherDB(Context context) {
		MinimalWeatherOpenHelper helper = new MinimalWeatherOpenHelper(context,
				DB_NAME, null, DB_VERSION);
		db = helper.getWritableDatabase();
	}

	/**
	 * 单利模式，取唯一实例
	 * 
	 * @param context
	 * @return
	 */
	public static MinimalWeatherDB getInstance(Context context) {
		if (minimalWeatherDB == null) {
			minimalWeatherDB = new MinimalWeatherDB(context);
		}
		return minimalWeatherDB;
	}

	/**
	 * 插入数据：省
	 * 
	 * @param city
	 */
	public void insertProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("PROVINCE_CODE", province.getProvinceCode());
			values.put("PROVINCE_NAME", province.getProvinceName());
			db.insert("PROVINCE", null, values);
		}
	}

	/**
	 * 插入数据：市
	 * 
	 * @param city
	 */
	public void insertCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("CITY_CODE", city.getCityCode());
			values.put("CITY_NAME", city.getCityName());
			values.put("PROVINCE_CODE", city.getProvinceCode());
			db.insert("CITY", null, values);
		}
	}

	/**
	 * 插入数据：区
	 * 
	 * @param city
	 */
	public void insertDistrict(District district) {
		if (district != null) {
			ContentValues values = new ContentValues();
			values.put("DISTRICT_CODE", district.getDistrictCode());
			values.put("DISTRICT_NAME", district.getDistrictName());
			values.put("CITY_NAME", district.getCityCode());
			db.insert("DISTRICT", null, values);
		}
	}

	public List<Province> getProvinces() {
		List<Province> list = new ArrayList<Province>();
		Province province = null;
		Cursor cursor = db
				.query("PROVINCE", null, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("PROVINCE_CODE")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("PROVINCE_NAME")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public List<City> getCities(String provinceCode) {
		List<City> list = new ArrayList<>();
		City city = null;
		Cursor cursor = db.query("CITY", null, "PROVINCE_CODE = ?",
				new String[] { provinceCode }, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				city.setProvinceCode(provinceCode);
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("CITY_NAME")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("CITY_CODE")));
				list.add(city);
			} while (cursor.moveToNext());
		}

		return list;

	}

	public List<District> getDistrict(String cityCode) {
		List<District> list = new ArrayList<>();
		District district = null;
		Cursor cursor = db.query("DISTRICT", null, "CITY_CODE=?",
				new String[] { cityCode }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				district = new District();
				district.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				district.setDistrictCode(cursor.getString(cursor
						.getColumnIndex("DISTRICT_CODE")));
				district.setDistrictName(cursor.getString(cursor
						.getColumnIndex("DISTRICT_NAME")));
				district.setCityCode(cityCode);
				list.add(district);
			} while (cursor.moveToNext());
		}

		return list;

	}

	/**
	 * 开启事务，结束后务必关闭事务
	 * 1、beginTranscation
	 * 2、setTransactionSuccessful
	 * 3、endTranscation
	 */
	public void beginTranscation() {
		db.beginTransaction();
	}

	/**
	 * 关闭事务
	 */
	public void endTranscation() {
		db.endTransaction();
	}
	
	public void setTransactionSuccessful(){
		db.setTransactionSuccessful();
	}
}

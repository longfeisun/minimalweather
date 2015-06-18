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
			values.put("province_code", province.getProvinceCode());
			values.put("province_name", province.getProvinceName());
			db.insert("province", null, values);
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
			values.put("city_code", city.getCityCode());
			values.put("city_name", city.getCityName());
			values.put("province_code", city.getProvinceCode());
			db.insert("city", null, values);
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
			values.put("district_code", district.getDistrictCode());
			values.put("district_name", district.getDistrictName());
			values.put("city_code", district.getCityCode());
			db.insert("district", null, values);
		}
	}

	public List<Province> getProvinces() {
		List<Province> list = new ArrayList<Province>();
		Province province = null;
		Cursor cursor = db
				.query("province", null, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
			} while (cursor.moveToNext());
		}
		return list;
	}

	public List<City> getCities(String provinceCode) {
		List<City> list = new ArrayList<>();
		City city = null;
		Cursor cursor = db.query("city", null, "province_code=?",
				new String[] { provinceCode }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
			} while (cursor.moveToNext());
		}

		return list;

	}

	public List<District> getDistrict(String provinceCode) {
		List<District> list = new ArrayList<>();
		District district = null;
		Cursor cursor = db.query("district", null, "city_code=?",
				new String[] { provinceCode }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				district = new District();
				district.setId(cursor.getInt(cursor.getColumnIndex("id")));
				district.setDistrictCode(cursor.getString(cursor
						.getColumnIndex("district_code")));
				district.setDistrictName(cursor.getString(cursor
						.getColumnIndex("district_name")));
				district.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
			} while (cursor.moveToNext());
		}

		return list;

	}

	/**
	 * 开启事务，结束后务必关闭事务
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
}

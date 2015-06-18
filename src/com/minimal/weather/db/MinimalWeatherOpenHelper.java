package com.minimal.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MinimalWeatherOpenHelper extends SQLiteOpenHelper {

	public MinimalWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	private static final String CREATE_PROVINCE = "CREATE TABLE PROVINCE (ID INTEGER PRIMARY KEY AUTOINCREMENT , PROVINCE_CODE TEXT , PROVINCE_NAME TEXT )";
	private static final String CREATE_CITY = "CREATE TABLE CITY(ID INTEGER PRIMARY KEY AUTOINCREMENT, CITY_CODE TEXT, CITY_NAME TEXT, PROVINCE_CODE TEXT )";
	private static final String CREATE_DISTRICT = "CREATE TABLE DISTRICT ( ID INTEGER PRIMARY KEY AUTOINCREMENT, DISTRICT_CODE TEXT, DISTRICT_NAME TEXT, CITY_CODE TEXT)";
	
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_DISTRICT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

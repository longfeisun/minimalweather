package com.minimal.weather.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {
	private static List<Activity> listActivity = new ArrayList<Activity>();
	
	public static void addActivity(Activity activity){
		listActivity.add(activity);
	}
	
	public static void removeActivity(Activity activity){
		listActivity.remove(activity);
	}
	
	public static void finishAll(){
		for(Activity activity : listActivity){
			activity.finish();
		}
	}
	
	
}

package com.minimal.weather.util;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.minimal.weather.db.MinimalWeatherDB;
import com.minimal.weather.entity.City;
import com.minimal.weather.entity.District;
import com.minimal.weather.entity.Province;

public class XmlUtil {
	public static void parseXml2Save(Context context, int id ) {
		MinimalWeatherDB db = MinimalWeatherDB.getInstance(context);
		
		
		
		
		try {
			db.beginTranscation();
			XmlResourceParser parser = context.getResources().getXml(id);
			Province province = null;
			City city = null;
			District district = null;
			
			//取第一个事件
			int eventType = parser.getEventType();
			//只要不是文档结束时间就循环
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String startTagName =parser.getName();
					if("p".equals(startTagName)){
						province = new Province();
						province.setProvinceCode(parser.getAttributeValue(0));
						
					}
					if("pn".equals(startTagName)){
						province.setProvinceName(parser.nextText());
					}
					if("c".equals(startTagName)){
						city = new City();
						city.setCityCode(parser.getAttributeValue(0));
						city.setProvinceCode(province.getProvinceCode());
					} 
					if("cn".equals(startTagName)){
						city.setCityName(parser.nextText());
					}
					if("d".equals(startTagName)){
						district = new District(); 
						district.setDistrictCode(parser.getAttributeValue(0));		
						district.setDistrictName(parser.nextText());
						district.setCityCode(city.getCityCode());
						db.insertDistrict(district);
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = parser.getName();
					if("c".equals(endTagName)){
						db.insertCity(city);
					}
					if("p".equals(endTagName)){
						db.insertProvince(province);
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			db.endTranscation();
		}

	}
	
}

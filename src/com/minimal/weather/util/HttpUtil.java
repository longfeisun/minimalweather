package com.minimal.weather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	/**
	 * 根据传入地址发送GET请求取输入流
	 * @param address
	 * @return
	 */
	public static InputStream sendHttpRequest4Inputstream(String address){
		try {
			URL url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(8000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if(conn.getResponseCode() == 200){
				return conn.getInputStream();
			}
		} catch (Exception e) {
			
		}
		return  null;
	}
	
	
	public static String sendHttpRequest4String(String address){
		StringBuilder response = new StringBuilder();
		try {
			URL url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(8000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if(conn.getResponseCode() == 200){
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = "";
				while((line = reader.readLine()) != null){
					response.append(line);
				}
			}
		} catch (Exception e) {
			
		}
		return  response.toString();
		
		
		
	}
	
	
	
}

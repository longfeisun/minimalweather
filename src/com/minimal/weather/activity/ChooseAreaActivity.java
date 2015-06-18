package com.minimal.weather.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Loader;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.minimal.weather.R;
import com.minimal.weather.db.MinimalWeatherDB;
import com.minimal.weather.entity.Province;

public class ChooseAreaActivity extends BaseActivity {

	private static final int LEVEL_PROVINCE = 1;
	private static final int LEVEL_CITY = 2;
	private static final int LEVEL_DISTRICT = 3;
	private int LEVEL_CURRENT = 4;
	private MinimalWeatherDB db;
	private List<Province> listProvince;
	private List<String> listData = new ArrayList<>();
	private ArrayAdapter<String> adapter ;
	private ListView lv_choose_area;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_area);
		
		lv_choose_area = (ListView) this.findViewById(R.id.lv_choose_area);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		
		adapter = new ArrayAdapter<>(ChooseAreaActivity.this, android.R.layout.simple_list_item_1, listData);
		lv_choose_area.setAdapter(adapter);
		
		db = MinimalWeatherDB.getInstance(ChooseAreaActivity.this);
		listProvince = db.getProvinces();
		
		loadProvince();
		
	}

	private void loadProvince() {
		listProvince = db.getProvinces();

		for (Province province : listProvince) {
			listData.add(province.getProvinceName());
		}
		
		adapter.notifyDataSetChanged();
		lv_choose_area.setSelection(0);
		LEVEL_CURRENT = LEVEL_PROVINCE;
		tv_title.setText("全国");
		
	}

}

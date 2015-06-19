package com.minimal.weather.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minimal.weather.R;
import com.minimal.weather.db.MinimalWeatherDB;
import com.minimal.weather.entity.City;
import com.minimal.weather.entity.District;
import com.minimal.weather.entity.Province;

public class ChooseAreaActivity extends BaseActivity {

	private static final int LEVEL_PROVINCE = 1;
	private static final int LEVEL_CITY = 2;
	private static final int LEVEL_DISTRICT = 3;
	private int LEVEL_CURRENT = 4;
	private MinimalWeatherDB db;

	private List<Province> listProvince;
	private List<City> listCity;
	private List<District> listDistrict;

	private Province selectedProvince;
	private City selectedCity;
	private District selectedDistrict;

	private boolean finishAllFlag = false;

	private List<String> listData = new ArrayList<>();
	private ArrayAdapter<String> adapter;
	private ListView lv_choose_area;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_area);

		lv_choose_area = (ListView) this.findViewById(R.id.lv_choose_area);
		tv_title = (TextView) this.findViewById(R.id.tv_title);

		adapter = new ArrayAdapter<>(ChooseAreaActivity.this,
				android.R.layout.simple_list_item_1, listData);
		lv_choose_area.setAdapter(adapter);
		db = MinimalWeatherDB.getInstance(ChooseAreaActivity.this);

		lv_choose_area.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (LEVEL_CURRENT == LEVEL_PROVINCE) {
					selectedProvince = listProvince.get(position);
					loadCity();
					Toast.makeText(ChooseAreaActivity.this,
							"" + selectedProvince.toString(),
							Toast.LENGTH_SHORT).show();
				} else if (LEVEL_CURRENT == LEVEL_CITY) {
					selectedCity = listCity.get(position);
					loadDistrict();
					Toast.makeText(ChooseAreaActivity.this,
							"" + selectedCity.toString(), Toast.LENGTH_SHORT)
							.show();

				} else if (LEVEL_CURRENT == LEVEL_DISTRICT) {
					selectedDistrict = listDistrict.get(position);
					Toast.makeText(ChooseAreaActivity.this,
							"" + selectedDistrict.toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		loadProvince();

	}

	private void loadProvince() {
		listProvince = db.getProvinces();
		if (listProvince != null && listProvince.size() > 0) {
			listData.clear();
			for (Province province : listProvince) {
				listData.add(province.getProvinceName());
			}

			adapter.notifyDataSetChanged();
			lv_choose_area.setSelection(0);
			LEVEL_CURRENT = LEVEL_PROVINCE;
			tv_title.setText("全国");
		}else {
//			queryFromRequest("province",null);
		}
	}

	private void loadCity() {
		listCity = db.getCities(selectedProvince.getProvinceCode());
		if (listCity != null && listCity.size() > 0) {
			listData.clear();
			for (City city : listCity) {
				listData.add(city.getCityName());
			}
			adapter.notifyDataSetChanged();
			lv_choose_area.setSelection(0);
			LEVEL_CURRENT = LEVEL_CITY;
			tv_title.setText(selectedProvince.getProvinceName());
		}else {
//			queryFromRequest("city",selectedProvince.getProvinceCode());
		}
	}

	private void loadDistrict() {
		listDistrict = db.getDistrict(selectedCity.getCityCode());
		if (listDistrict != null && listDistrict.size() > 0) {
			listData.clear();
			for (District district : listDistrict) {
				listData.add(district.getDistrictName());
			}
			adapter.notifyDataSetChanged();
			lv_choose_area.setSelection(0);
			LEVEL_CURRENT = LEVEL_DISTRICT;
			tv_title.setText(selectedCity.getCityName());
		} else {
//			queryFromRequest("district",selectedCity.getCityCode());
		}
	}

	@Override
	public void onBackPressed() {

		if (LEVEL_CURRENT == LEVEL_DISTRICT) {
			finishAllFlag = false;
			loadCity();
		} else if (LEVEL_CURRENT == LEVEL_CITY) {
			finishAllFlag = false;
			loadProvince();
		} else if (LEVEL_CURRENT == LEVEL_PROVINCE) {
			if (finishAllFlag) {
				ActivityCollector.finishAll();
			}
			finishAllFlag = true;
			Toast.makeText(ChooseAreaActivity.this, "再按一次退出应用",
					Toast.LENGTH_SHORT).show();
		}
	}

//	public void queryFromRequest(final String flag, final String code) {
//
//		String path = "http://www.weather.com.cn/data/list3/city"
//				+ (TextUtils.isEmpty(code) ? "" : code) + ".xml";
//
//		HttpUtil.sendHttpRequest(path, new CallBackListener() {
//
//			@Override
//			public void onFinished(String response) {
//				boolean result = false;
//				if ("province".equals(flag)) {
//					result = Utility.handleProvincesResponse(db, response);
//				} else if ("city".equals(flag)) {
//					result = Utility.handleCitiesResponse(db, response, code);
//				} else if ("district".equals(flag)) {
//					result = Utility.handleCountiesResponse(db, response, code);
//				}
//
//				if (result) {
//					// 在UI线程更新界面
//					runOnUiThread(new Runnable() {
//						public void run() {
//
//							if ("province".equals(flag)) {
//								loadProvince();
//							} else if ("city".equals(flag)) {
//								loadCity();
//							} else if ("county".equals(flag)) {
//								loadDistrict();
//							}
//
//						}
//					});
//				}
//			}
//
//			@Override
//			public void onError(Exception e) {
//				LogUtil.i(TAG, e.getMessage());
//			}
//		});
//
//	}

}

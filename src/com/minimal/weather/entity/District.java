package com.minimal.weather.entity;

public class District {
	private int id;
	private String districtCode;
	private String districtName;
	private String cityCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Override
	public String toString() {
		return "District [id=" + id + ", districtCode=" + districtCode
				+ ", districtName=" + districtName + ", cityCode=" + cityCode
				+ "]";
	}
	
}

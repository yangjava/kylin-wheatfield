/*
 * Powered By rkylin-code-generator
 * Web Site: http://www.chanjetpay.com
 * Since 2014 - 2015
 */

package com.rkylin.wheatfield.bean;

import java.io.Serializable;

/**
 * CityCodeQuery
 * @author code-generator
 *
 */
public class CityCodeQuy implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private java.lang.String cityCode;
	private java.lang.String cityLevel;
	private java.lang.String cityName;
	private java.lang.String superCityCode;

	/**
	 * 省市编码
	 * @param cityCode
	 */
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
	 * 省市编码
	 * @return
	 */
	public java.lang.String getCityCode() {
		return this.cityCode;
	}
	/**
	 * 省市级别
	 * @param cityLevel
	 */
	public void setCityLevel(java.lang.String cityLevel) {
		this.cityLevel = cityLevel;
	}
	
	/**
	 * 省市级别
	 * @return
	 */
	public java.lang.String getCityLevel() {
		return this.cityLevel;
	}
	/**
	 * 省市名称
	 * @param cityName
	 */
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	
	/**
	 * 省市名称
	 * @return
	 */
	public java.lang.String getCityName() {
		return this.cityName;
	}
	/**
	 * 父级编码
	 * @param superCityCode
	 */
	public void setSuperCityCode(java.lang.String superCityCode) {
		this.superCityCode = superCityCode;
	}
	
	/**
	 * 父级编码
	 * @return
	 */
	public java.lang.String getSuperCityCode() {
		return this.superCityCode;
	}
}
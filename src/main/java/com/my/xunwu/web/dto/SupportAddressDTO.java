package com.my.xunwu.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 中间转换对象（保护数据）
 * @author hcz
 * @date 2019.10.21
 */
public class SupportAddressDTO {
	
	private Long id;
	
	@JsonProperty(value="belong_to")
	private String belongTo;
	
	@JsonProperty(value="en_name")
	private String enName;
	
	@JsonProperty(value="cn_name")
	private String cnName;
	
	private String level;
	
	private double baiduMapLng;
	
	private double baiduMapLat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public double getBaiduMapLng() {
		return baiduMapLng;
	}

	public void setBaiduMapLng(double baiduMapLng) {
		this.baiduMapLng = baiduMapLng;
	}

	public double getBaiduMapLat() {
		return baiduMapLat;
	}

	public void setBaiduMapLat(double baiduMapLat) {
		this.baiduMapLat = baiduMapLat;
	}
}


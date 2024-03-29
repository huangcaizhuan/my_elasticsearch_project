package com.my.xunwu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 房屋图片信息
 * @author hcz
 * @date 2019.10.25
 */
@Entity
@Table(name="house_picture")
public class HousePicture {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="house_id")
	private Long houseId;
	
	@Column(name="cdn_prefix")
	private String cdnPrefix;
	
	private int width;
	
	private int height;
	
	private String location;
	
	private String path;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getCdnPrefix() {
		return cdnPrefix;
	}

	public void setCdnPrefix(String cdnPrefix) {
		this.cdnPrefix = cdnPrefix;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}

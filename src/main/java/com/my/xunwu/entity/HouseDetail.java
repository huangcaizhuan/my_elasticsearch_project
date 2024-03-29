package com.my.xunwu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author hcz
 * @date 2019.10.25
 */
@Entity
@Table(name="house_detail")
public class HouseDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String description;
	
	@Column(name="layout_desc")
	private String layoutDesc;
	
	private String traffic;
	
	@Column(name="round_service")
	private String roundService;
	
	@Column(name="rent_way")
	private int rentWay;
	
	@Column(name = "address")
	private String detailAddress;
	
	@Column(name="subway_line_id")
	private Long subwayLineId;
	
	@Column(name="subway_line_name")
	private String subwayLineName;
	
	@Column(name="subway_station_id")
	private Long subwayStationId;
	
	@Column(name="subway_station_name")
	private String subwayStationName;
	
	@Column(name="house_id")
	private Long houseId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLayoutDesc() {
		return layoutDesc;
	}

	public void setLayoutDesc(String layoutDesc) {
		this.layoutDesc = layoutDesc;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getRoundService() {
		return roundService;
	}

	public void setRoundService(String roundService) {
		this.roundService = roundService;
	}

	public int getRentWay() {
		return rentWay;
	}

	public void setRentWay(int rentWay) {
		this.rentWay = rentWay;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public Long getSubwayLineId() {
		return subwayLineId;
	}

	public void setSubwayLineId(Long subwayLineId) {
		this.subwayLineId = subwayLineId;
	}

	public String getSubwayLineName() {
		return subwayLineName;
	}

	public void setSubwayLineName(String subwayLineName) {
		this.subwayLineName = subwayLineName;
	}

	public Long getSubwayStationId() {
		return subwayStationId;
	}

	public void setSubwayStationId(Long subwayStationId) {
		this.subwayStationId = subwayStationId;
	}

	public String getSubwayStationName() {
		return subwayStationName;
	}

	public void setSubwayStationName(String subwayStationName) {
		this.subwayStationName = subwayStationName;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}
	
}

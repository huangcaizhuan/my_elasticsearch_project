package com.my.xunwu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.my.xunwu.entity.SubwayStation;

public interface SubwayStationRepository extends CrudRepository<SubwayStation,Long>{
	List<SubwayStation> findAllBySubwayId(Long subwayId);
}

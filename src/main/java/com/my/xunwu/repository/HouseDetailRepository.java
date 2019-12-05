package com.my.xunwu.repository;

import org.springframework.data.repository.CrudRepository;

import com.my.xunwu.entity.HouseDetail;

public interface HouseDetailRepository extends CrudRepository<HouseDetail, Long>{

	HouseDetail findAllByHouseId(Long id);

}

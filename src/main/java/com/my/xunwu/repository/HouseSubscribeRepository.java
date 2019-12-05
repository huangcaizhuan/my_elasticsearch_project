package com.my.xunwu.repository;

import org.springframework.data.repository.CrudRepository;

import com.my.xunwu.entity.HouseSubscribe;

public interface HouseSubscribeRepository extends CrudRepository<HouseSubscribe, Long>{

	//HouseSubscribe findByHouseIdAndUserId(Long id, Long loginUserId);

}

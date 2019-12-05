package com.my.xunwu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.my.xunwu.entity.HouseTag;

public interface HouseTagRepository extends CrudRepository<HouseTag, Long>{

	List<HouseTag> findAllByHouseId(Long id);

}

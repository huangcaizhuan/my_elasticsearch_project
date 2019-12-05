package com.my.xunwu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.my.xunwu.entity.HousePicture;

public interface HousePictureRepository extends CrudRepository<HousePicture, Long>{

	List<HousePicture> findAllByHouseId(Long id);

}

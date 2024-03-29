package com.my.xunwu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.my.xunwu.entity.House;

public interface HouseRepository extends PagingAndSortingRepository<House, Long>,
JpaSpecificationExecutor<House>{

}

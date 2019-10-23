package com.my.xunwu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.my.xunwu.entity.Subway;
/**
 * 地铁接口服务
 * @author hcz
 * @date 2019.10.23
 */
public interface SubwayRepository extends CrudRepository<Subway, Long>{
	List<Subway> findAllByCityEnName(String cityEnName);
}

package com.my.xunwu.service;

import java.util.List;

/**
 * 通用多结果Service返回结构
 * @author hcz
 * @date 2019.10.21
 * @param <T>
 */
public class ServiceMultiResult<T> {
	private long id;
	private List<T> result;
	public ServiceMultiResult(long id, List<T> result) {
		super();
		this.id = id;
		this.result = result;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	
	public int getResultSize() {
        if (this.result == null) {
            return 0;
        }
        return this.result.size();
    }
}

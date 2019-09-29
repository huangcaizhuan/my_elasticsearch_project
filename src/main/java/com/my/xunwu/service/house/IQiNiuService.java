package com.my.xunwu.service.house;
/**
 * 七牛云服务
 * @author hcz
 * @date 2019.9.29
 */

import java.io.File;
import java.io.InputStream;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

public interface IQiNiuService {
	Response uploadFile(File file) throws QiniuException;
	
	Response uploadFile(InputStream inputStream) throws QiniuException;
	
	Response delete(String key) throws QiniuException;
}

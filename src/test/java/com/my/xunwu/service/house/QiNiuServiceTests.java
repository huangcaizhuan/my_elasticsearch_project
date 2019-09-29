package com.my.xunwu.service.house;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.xunwu.ApplicationTests;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

public class QiNiuServiceTests extends ApplicationTests{
	@Autowired
    private IQiNiuService qiNiuService;

    @Test
    public void testUploadFile() {
        String fileName = "F:/test/xunwu/images/20190927112633.png";
        File file = new File(fileName);

        Assert.assertTrue(file.exists());

        try {
            Response response = qiNiuService.uploadFile(file);
            Assert.assertTrue(response.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

}

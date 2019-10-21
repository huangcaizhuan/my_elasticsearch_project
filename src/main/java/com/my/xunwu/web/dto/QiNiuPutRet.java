package com.my.xunwu.web.dto;

/**
 * 七牛返回数据转换
 * @author hcz
 * @date 2019.10.21
 */
public final class QiNiuPutRet {
	public String key;
    public String hash;
    public String bucket;
    public int width;
    public int height;

    @Override
    public String toString() {
        return "QiNiuPutRet{" +
                "key='" + key + '\'' +
                ", hash='" + hash + '\'' +
                ", bucket='" + bucket + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

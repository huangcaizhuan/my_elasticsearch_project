package com.my.xunwu.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 文件上传配置
 * @author hcz
 * @date 2019.9.27
 */
@Configuration
@ConditionalOnClass({Servlet.class,StandardServletMultipartResolver.class,MultipartConfigElement.class})
@ConditionalOnProperty(prefix="spring.http.multipart",name="enabled",matchIfMissing=true)
@EnableConfigurationProperties(MultipartProperties.class)
public class WebUploadFileConfig {

	private final MultipartProperties multipartProperties;

	public WebUploadFileConfig(MultipartProperties multipartProperties) {
		this.multipartProperties = multipartProperties;
	}
	
	/**
	 * 上传配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public MultipartConfigElement multipartConfigElement() {
		return this.multipartProperties.createMultipartConfig();
	}
	
	/**
     * 注册解析器
     */
    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean(MultipartResolver.class)
    public StandardServletMultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        multipartResolver.setResolveLazily(this.multipartProperties.isResolveLazily());
        return multipartResolver;
    }
    
    /**
     * 七牛云华东地区机房（注册账号所分配的地区）
     * 华东地区：Zone.zone0()
     * @return
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig(){
    	return new com.qiniu.storage.Configuration(Zone.zone0());
    }
    
    /**
     * 构建一个七牛云上传工具实例
     * @return
     */
    @Bean
    public UploadManager uploadManager() {
    	return new UploadManager(qiniuConfig());
    }
    
    @Value("${qiniu.AccessKey}")
    private String accessKey;
    @Value("${qiniu.SecretKey}")
    private String secretKey;
    
    /**
     * 七牛云认证信息实例
     * @return
     */
    @Bean
    public Auth auth() {
    	return Auth.create(accessKey, secretKey);
    }
    
    /**
     * 构建七牛云空间管理实例
     * @return
     */
    @Bean
    public BucketManager bucketManager() {
    	return new BucketManager(auth(),qiniuConfig());
    }
}

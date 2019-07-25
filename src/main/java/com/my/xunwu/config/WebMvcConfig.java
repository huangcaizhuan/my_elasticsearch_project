package com.my.xunwu.config;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;


/**
 * 前端资源配置
 * @author hcz
 * 2019.7.25
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext = context;//上下文
	}

	/**
	 * 静态资源加载配置
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}
	/**
	 * 模板资源解析器
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.thymeleaf")
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(applicationContext);
		return templateResolver;
	}
	
	/**
	 * thymeleaf标准方言解释器
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		//支持spring EL表达式
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}
	
	/**
	 * 视图解析器
	 */
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}
	
}

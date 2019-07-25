package com.my.xunwu.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA配置类,数据库实体配置
 * @author hcz
 * 2019.7.24
 */
@Configuration//配置类
@EnableJpaRepositories(basePackages = "com.my.xunwu.repository")//JPA基础包
@EnableTransactionManagement//事物管理
public class JPAConfig {

	/**
	 * 数据库连接
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")//application.properties的spring.datasource
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	/**
	 * 实体管理
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(false);//是否为sql，sql语句掌握在自己手中
		
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());//连接数据库
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);//使用Hibernate
		entityManagerFactory.setPackagesToScan("com.my.xunwu.entity");//扫描实体
		return entityManagerFactory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		return jpaTransactionManager;
	}
}

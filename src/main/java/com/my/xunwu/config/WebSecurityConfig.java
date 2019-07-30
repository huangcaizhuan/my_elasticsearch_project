package com.my.xunwu.config;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * @author huangcaizhuan
 * 2019.7.30
 */
@EnableWebSecurity//允许使用WebSecurity
@EnableGlobalMethodSecurity//允许使用GlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	/**
	 * HTTP权限控制
	 * @throws Exception 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//资源访问权限
		http.authorizeRequests()
			.antMatchers("/admin/login").permitAll()//管理员登录入口
			.antMatchers("/static/**").permitAll()//静态资源
			.antMatchers("/user/login").permitAll()//用户登录入口
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/user/**").hasAnyRole("ADMIN")
			.antMatchers("api/user/**").hasAnyRole("ADMIN","USER")
			.and()
			.formLogin()
			.loginProcessingUrl("/login")//配置角色登录入口
			.and();
		
		http.csrf().disable();//先关掉防御策略
		http.headers().frameOptions().sameOrigin();//同源策略
	}
	
	/**
	 * 自定义认证策略
	 * @throws Exception 
	 */
	@Autowired
	public void confingGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin")
		.roles("ADMIN").and();
		
	}
}

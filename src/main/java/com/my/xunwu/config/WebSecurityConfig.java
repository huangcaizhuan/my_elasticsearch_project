package com.my.xunwu.config;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.my.xunwu.security.AuthProvider;
import com.my.xunwu.security.LoginUrlEntryPoint;

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
			.antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
			.antMatchers("/api/user/**").hasAnyRole("ADMIN","USER")
			.and()
			.formLogin()
			.loginProcessingUrl("/login")//配置角色登录入口
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/logout/page")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(urlEntryPoint())
			.accessDeniedPage("/403");
		
		http.csrf().disable();//先关掉防御策略
		http.headers().frameOptions().sameOrigin();//同源策略
	}
	
	/**
	 * 自定义认证策略
	 * @throws Exception 
	 */
	@Autowired
	public void confingGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//内存定义登录认证（登录名、密码、角色）
		/*auth.inMemoryAuthentication().withUser("admin").password("admin")
		.roles("ADMIN").and();*/
		
		//数据库登录认证
		auth.authenticationProvider(authProvider()).eraseCredentials(true);
	}
	
	@Bean
	public AuthProvider authProvider() {
		 return new AuthProvider();
	}
	
	@Bean
	public LoginUrlEntryPoint urlEntryPoint() {
		 return new LoginUrlEntryPoint("/user/login");
	}
}

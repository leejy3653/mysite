package kr.co.itcen.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kr.co.itcen.mysite.security.AuthInterceptor;
import kr.co.itcen.mysite.security.AuthUserHandlerMethodArgumentResolver;
import kr.co.itcen.mysite.security.LoginInterceptor;
import kr.co.itcen.mysite.security.LogoutInterceptor;
/**
 * 1. Interceptor 설정
 * 2. Argument Resolver 설정
 * 
 *
 */
@EnableWebMvc
@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter{//<mvc:interceptors>
	
	//Argument Resolver
	@Bean
	AuthUserHandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}
	
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}


	//Interceptors
	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public LogoutInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(loginInterceptor()).addPathPatterns("/user/auth");
		registry.addInterceptor(logoutInterceptor()).addPathPatterns("/user/logout");
		registry.addInterceptor(authInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/auth").excludePathPatterns("/user/logout").excludePathPatterns("/assets/**");
		
	}
	
	
}

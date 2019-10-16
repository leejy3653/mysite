package kr.co.itcen.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import kr.co.itcen.config.web.MVCConfig;

@Configuration
//@EnableWebMvc //MVCConfig에서 해준다
@EnableAspectJAutoProxy//<aop:aspectj-autoproxy />
@Import({MVCConfig.class})
@ComponentScan({ "kr.co.itcen.mysite.controller" }) // auto-scan
public class WebConfig {
	
}

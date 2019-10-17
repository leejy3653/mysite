package kr.co.itcen.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import kr.co.itcen.config.web.MVCConfig;
import kr.co.itcen.config.web.SecurityConfig;

@Configuration
//@EnableWebMvc //MVCConfig에서 해준다
@EnableAspectJAutoProxy//<aop:aspectj-autoproxy />
@ComponentScan({ "kr.co.itcen.mysite.controller" }) // auto-scan
@Import({MVCConfig.class, SecurityConfig.class})
public class WebConfig {
	
}

package kr.co.itcen.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import kr.co.itcen.config.app.DBConfig;
import kr.co.itcen.config.app.MybatisConfig;

@Configuration
@EnableAspectJAutoProxy // application Context의 <aop:aspectj-autoproxy />역할
@ComponentScan({ "kr.co.itcen.mysite.service", "kr.co.itcen.mysite.repository", "kr.co.itcen.mysite.aspect" })
// application Context의 <context:component-scan>
@Import({DBConfig.class, MybatisConfig.class})
public class AppConfig {
}

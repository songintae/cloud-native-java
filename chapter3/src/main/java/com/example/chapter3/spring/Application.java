package com.example.chapter3.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:/some.properties")
public class Application {

    private final Log log = LogFactory.getLog(Application.class);

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Application.class);
    }

    /**
     * PropertySourcesPlaceholderConfigurer는 BeanFactoryPostProcessor의 구현체이고
     * 스프링 빈 생성 수명 주기에서 초기에 호출 되어야 하므로 static빈으로 등록해야 한다.
     * https://docs.spring.io/spring-framework/docs/5.3.10-SNAPSHOT/reference/html/core.html#beans-factory-placeholderconfigurer
     *
     * @return
     */
    @Bean
    static PropertySourcesPlaceholderConfigurer pspc() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${configuration.projectName}")
    private String fieldValue;

    @Autowired
    Application(@Value("${configuration.projectName}") String pn) {
        log.info("Application constructor : " + pn);
    }

    @Value("${configuration.projectName}")
    public void setProjectName(String projectName) {
        log.info("Project Name : " + projectName);
    }

    @Autowired
    private void setEnvironment(Environment environment) {
        log.info("setEnvironment : " + environment.getProperty("configuration.projectName"));
    }

    @PostConstruct
    void afterPropertiesSet() throws Throwable {
        log.info("fieldValue : " + this.fieldValue);
    }
}

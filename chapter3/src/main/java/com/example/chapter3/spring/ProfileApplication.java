package com.example.chapter3.spring;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class ProfileApplication {

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Configuration
    @Profile("prod")
    @PropertySource("classpath:/some-prod.properties")
    public static class ProdConfiguration {
        @Bean
        InitializingBean init() {
            return new InitializingBean() {
                @Override
                public void afterPropertiesSet() {
                    LogFactory.getLog(getClass()).info("prod Initializing Bean");
                }
            };
        }
    }

    @Configuration
    @Profile({"default", "dev"})
    @PropertySource("classpath:/some.properties")
    public static class DefaultConfiguration {
        @Bean
        InitializingBean init() {
            return new InitializingBean() {
                @Override
                public void afterPropertiesSet() {
                    LogFactory.getLog(getClass()).info("default Initializing Bean");
                }
            };
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.getEnvironment().setActiveProfiles("dev");
        ac.register(ProfileApplication.class);
        ac.refresh();
    }
}

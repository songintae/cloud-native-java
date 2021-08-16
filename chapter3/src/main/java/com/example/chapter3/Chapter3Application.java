package com.example.chapter3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties
@SpringBootApplication
public class Chapter3Application {

    private static final Log log = LogFactory.getLog(Chapter3Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Chapter3Application.class, args);
    }

    @Autowired
    public Chapter3Application(ConfigurationProjectProperties properties) {
        log.info("ConfigurationProjectProperties.projectName = " + properties.getProjectName());
    }

}

@Component
@ConfigurationProperties("configuration")
class ConfigurationProjectProperties {
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

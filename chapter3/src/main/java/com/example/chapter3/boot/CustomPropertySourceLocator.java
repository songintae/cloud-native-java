package com.example.chapter3.boot;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Collections;

/**
 * PropertySourceLocator를 구현함으로서 다양한 외부 소스에서 커스터마이징 해서 데이터를 가져올 수 있다.
 * https://docs.spring.io/spring-cloud-commons/docs/3.0.3/reference/html/#customizing-bootstrap-property-sources
 */
public class CustomPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        return new MapPropertySource("customProperty",
                Collections.singletonMap("property.from.custom.source", "custom source properties"));
    }
}

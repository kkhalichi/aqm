package com.khalichi.atcq.framework.swagger;

import com.khalichi.atcq.framework.cxf.CustomSwagger2Feature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 */
@Configuration
@EnableConfigurationProperties(Swagger2Properties.class)
public class Swagger2AutoConfiguration implements InitializingBean {

    @Autowired
    private Swagger2Properties properties;

    @Override
    public void afterPropertiesSet() throws Exception {
        CustomSwagger2Feature.properties(this.properties);
    }
}

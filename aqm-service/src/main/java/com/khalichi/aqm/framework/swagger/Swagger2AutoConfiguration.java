package com.khalichi.aqm.framework.swagger;

import com.khalichi.aqm.framework.cxf.CustomSwagger2Feature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot auto-configuration of Swagger 2.
 */
@Configuration
@EnableConfigurationProperties(Swagger2Properties.class)
public class Swagger2AutoConfiguration implements InitializingBean {

    @Autowired
    private Swagger2Properties properties;

    /**
     * Manually assign Swagger 2 auto-configuration properties to CXF feature for its consumption. <p/>
     * {@inheritDoc}
     * */
    @Override
    public void afterPropertiesSet() throws Exception {
        CustomSwagger2Feature.properties(this.properties);
    }
}

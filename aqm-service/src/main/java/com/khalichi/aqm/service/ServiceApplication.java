package com.khalichi.atcq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Main class for this Microservice API.
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@SpringBootApplication(scanBasePackages = "com.khalichi")
public class ServiceApplication {

    /**
     * Main method.
     * @param theArgs
     */
    public static void main(String... theArgs) {
        SpringApplication.run(ServiceApplication.class, theArgs);
    }

    /**
     * Enables returning JSON from CXF REST services, in particular collections that are not wrapped in other containing objects.
     * @return an instance of the {@link JacksonJsonProvider}
     */
    @Bean
   	@ConditionalOnMissingBean
    public JacksonJsonProvider jsonProvider() {
   		JacksonJaxbJsonProvider aProvider = new JacksonJaxbJsonProvider();
   		aProvider.setMapper(new ObjectMapper());
   		return aProvider;
   	}
}

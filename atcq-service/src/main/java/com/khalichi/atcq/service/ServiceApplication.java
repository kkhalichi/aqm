package com.khalichi.atcq.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@SpringBootApplication(scanBasePackages = "com.khalichi")
public class ServiceApplication {

    public static void main(String... theArgs) {
        SpringApplication.run(ServiceApplication.class, theArgs);
    }
}

package com.khalichi.aqm.framework.cxf;

import com.khalichi.aqm.framework.swagger.Swagger2Properties;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;

/**
 * Custom CXF feature used to initialize Swagger.
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
public class CustomSwagger2Feature extends Swagger2Feature {

    private static Swagger2Properties properties = null;


    /** {@inheritDoc} */
    @Override
    protected void addSwaggerResource(final Server theServer, final Bus theBus) {
        this.setBasePath(properties.getBasePath());
        this.setTitle(properties.getTitle());
        this.setDescription(properties.getDescription());
        this.setContact(properties.getContact());
        this.setLicense(properties.getLicense());
        this.setLicenseUrl(properties.getLicenseUrl());
        this.setVersion(properties.getVersion());
        this.setPrettyPrint(properties.isPrettyPrint());
        super.addSwaggerResource(theServer, theBus);
    }

    /**
     * Since CXF features are not recognized by Spring Boot, this mechanism allows for manual assignment of Swagger auto-configuration to this feature.
     * Assignment can only be made once per class loader instance.
     * @param theProperties the auto-configuration properties
     */
    public static void properties(final Swagger2Properties theProperties) {
        if (properties == null) {
            properties = theProperties;
        }
        else {
            throw new RuntimeException(
                    "The properties this class has already been configured.  Subsequent configuration of this class is not allowed."
            );
        }
    }
}

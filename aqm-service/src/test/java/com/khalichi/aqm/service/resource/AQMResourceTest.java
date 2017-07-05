package com.khalichi.aqm.service.resource;

import javax.ws.rs.core.Response;
import com.khalichi.aqm.data.Aircraft;
import static com.khalichi.aqm.data.Aircraft.AircraftSize;
import static com.khalichi.aqm.data.Aircraft.AircraftType;

import com.khalichi.aqm.service.ServiceApplication;
import org.apache.cxf.jaxrs.client.spring.EnableJaxRsWebClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static javax.ws.rs.core.Response.Status;

import static org.junit.Assert.*;

/**
 * Tests for {@link AQMResource} implementation.
 * @author Keivan Khalichi
 * @since Jul 02, 2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@EnableJaxRsWebClient
public class AQMResourceTest {

    /** JAX-RS client proxy */
    @Autowired
    private AQMResource aqmResource;

    private static boolean booted = false;

    /**
     * Tests {@link AQMResource#boot()}.
     * @throws Exception
     */
    @Before
    public void boot() throws Exception {
        if (!booted) {
            Response aResponse = this.aqmResource.boot();
            assertEquals("Expected OK, received other.", Status.OK.getStatusCode(), aResponse.getStatus());
            aResponse = this.aqmResource.boot();
            assertEquals("Expected NOT_MODIFIED, received other.", Status.NOT_MODIFIED.getStatusCode(), aResponse.getStatus());
            booted = true;
        }
    }

    /**
     * Tests {@link AQMResource#enqueue(AircraftType, AircraftSize)}.
     * @throws Exception
     */
    @Test
    public void enqueue() throws Exception {
        this.enqueue(null, AircraftSize.SMALL);
        this.enqueue(AircraftType.PASSENGER, null);
        this.enqueue(AircraftType.PASSENGER, AircraftSize.SMALL);
    }

    /**
     * Tests {@link AQMResource#dequeue()}.
     * @throws Exception
     */
    @Test
    public void dequeue() throws Exception {
        this.enqueue(AircraftType.CARGO, AircraftSize.LARGE);
        this.enqueue(AircraftType.PASSENGER, AircraftSize.LARGE);
        this.enqueue(AircraftType.CARGO, AircraftSize.SMALL);

        this.dequeue(AircraftType.PASSENGER, AircraftSize.LARGE);
        this.dequeue(AircraftType.PASSENGER, AircraftSize.SMALL);
        this.dequeue(AircraftType.CARGO, AircraftSize.LARGE);
        this.dequeue(AircraftType.CARGO, AircraftSize.SMALL);
    }

    private void enqueue(final AircraftType theAircraftType, final AircraftSize theAircraftSize) {
        final Response aResponse = this.aqmResource.enqueue(theAircraftType, theAircraftSize);
        if ((theAircraftType == null) || (theAircraftSize == null)) {
            assertEquals("Expected NOT MODIFIED, received other.", Status.NOT_MODIFIED.getStatusCode(), aResponse.getStatus());
        }
        else {
            assertEquals("Expected OK, received other.", Status.OK.getStatusCode(), aResponse.getStatus());
            assertEquals("Expected instanceof Aircraft, received other.", Aircraft.class, aResponse.getEntity().getClass());
            Aircraft anAircraft = (Aircraft) aResponse.getEntity();
            assertEquals(String.format("Expected %s, received other.", theAircraftType), theAircraftType, anAircraft.getType());
            assertEquals(String.format("Expected %s, received other.", theAircraftSize), theAircraftSize, anAircraft.getSize());
        }
    }

    private void dequeue(final AircraftType theExpectedType, final AircraftSize theExpectedSize) {
        final Response aResponse = this.aqmResource.dequeue();
        assertEquals("Expected OK, received other.", Status.OK.getStatusCode(), aResponse.getStatus());
        assertEquals("Expected instanceof Aircraft, received other.", Aircraft.class, aResponse.getEntity().getClass());
        Aircraft anAircraft = (Aircraft) aResponse.getEntity();
        assertEquals(String.format("Expected %s, received other.", theExpectedType), theExpectedType, anAircraft.getType());
        assertEquals(String.format("Expected %s, received other.", theExpectedSize), theExpectedSize, anAircraft.getSize());
    }
}
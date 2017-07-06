package com.khalichi.aqm.service.resource;

import java.util.List;
import com.khalichi.aqm.data.Aircraft;
import com.khalichi.aqm.framework.cxf.CustomSwagger2Feature;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.khalichi.aqm.processor.AQMProcessor;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import static com.khalichi.aqm.data.Aircraft.AircraftSize;
import static com.khalichi.aqm.data.Aircraft.AircraftType;

/**
 * Implementation of {@link AQMResource}.  Initializes Swagger API documentation using a custom CXF feature.
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@Service("aqmResource")
@Features(classes = CustomSwagger2Feature.class)
public class AQMResourceImpl implements AQMResource {

    @Autowired
    private AQMProcessor aqmProcessor;

    /** {@inheritDoc} */
    @Override
    public Response boot() {
        Response aReturnValue;

        try {
            this.aqmProcessor.boot();
            aReturnValue = Response.ok("System boot complete.").build();

        }
        catch (Exception e) {
            aReturnValue = Response.notModified(e.getMessage()).build();
        }
        return aReturnValue;
    }

    /** {@inheritDoc} */
    @Override
    public Response enqueue(final AircraftType theAircraftType, final AircraftSize theAircraftSize) {
        Response aReturnValue;

        if ((theAircraftType == null) || (theAircraftSize == null)) {
            aReturnValue = Response.notModified("One or both parameters are null.").type(MediaType.TEXT_PLAIN).build();
        }
        else {
            try {
                final Aircraft anAircraft = new Aircraft(theAircraftType, theAircraftSize);
                if (this.aqmProcessor.enqueue(anAircraft)) {
                    aReturnValue = Response.ok(anAircraft).build();
                }
                else {
                    aReturnValue = Response.notModified(String.format("Enqueue of %s %s aircraft failed.", theAircraftSize, theAircraftType)).build();
                }
            }
            catch (Exception e) {
                aReturnValue = Response.notModified(e.getMessage()).build();
            }
        }
        return aReturnValue;
    }

    /** {@inheritDoc} */
    @Override
    public Response enqueue(final List<Aircraft> theAircraftList) {
        Response aReturnValue;

        if (CollectionUtils.isEmpty(theAircraftList)) {
            aReturnValue = Response.notModified("List of aircraft is null or empty.").type(MediaType.TEXT_PLAIN).build();
        }
        else {
            try {
                this.aqmProcessor.enqueue(theAircraftList);
                aReturnValue = Response.ok(this.aqmProcessor.dump()).build();
            }
            catch (Exception e) {
                aReturnValue = Response.notModified(e.getMessage()).build();
            }
        }
        return aReturnValue;
    }

    /** {@inheritDoc} */
    @Override
    public Response dequeue() {
        Response aReturnValue;

        try {
            final Aircraft anAircraft = this.aqmProcessor.dequeue();
            if (anAircraft == null) {
                aReturnValue = Response.notModified("There are no aircraft in the system.").type(MediaType.TEXT_PLAIN).build();
            }
            else {
                aReturnValue = Response.ok(anAircraft).build();
            }
        }
        catch (Exception e) {
            aReturnValue = Response.notModified(e.getMessage()).build();
        }
        return aReturnValue;
    }

    /** {@inheritDoc} */
    @Override
    public Response dump() {
        Response aReturnValue;

        try {
            aReturnValue = Response.ok(this.aqmProcessor.dump()).build();
        }
        catch (Exception e) {
            aReturnValue = Response.notModified(e.getMessage()).build();
        }
        return aReturnValue;
    }

    /** {@inheritDoc} */
    @Override
    public Response sortDump() {
        Response aReturnValue;

        try {
            aReturnValue = Response.ok(this.aqmProcessor.sortDump()).build();
        }
        catch (Exception e) {
            aReturnValue = Response.notModified(e.getMessage()).build();
        }
        return aReturnValue;
    }
}

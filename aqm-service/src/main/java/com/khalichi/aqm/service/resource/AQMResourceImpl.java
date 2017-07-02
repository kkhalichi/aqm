package com.khalichi.aqm.service.resource;

import java.util.List;
import com.khalichi.aqm.data.Aircraft;
import com.khalichi.aqm.framework.cxf.CustomSwagger2Feature;
import javax.ws.rs.core.Response;
import com.khalichi.aqm.manager.AQMProcessor;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import static com.khalichi.aqm.data.Aircraft.AircraftSize;
import static com.khalichi.aqm.data.Aircraft.AircraftType;

/**
 * Implementation of {@link ATCQueueResource}.  Initializes Swagger API documentation using a custom CXF feature.
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@Service("partnerProxy")
@Features(classes = CustomSwagger2Feature.class)
public class ATCQueueResourceImpl implements ATCQueueResource {

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

        if ((theAircraftType != null) && (theAircraftSize != null)) {
            try {
                final Aircraft anAircraft = new Aircraft(theAircraftType, theAircraftSize);
                this.aqmProcessor.enqueue(anAircraft);
                aReturnValue = Response.ok(
                                   String.format("Enqueue of %s %s aircraft (%s) complete.", theAircraftSize, theAircraftType, anAircraft.getUuid()))
                               .build();
            }
            catch (Exception e) {
                aReturnValue = Response.notModified(e.getMessage()).build();
            }
        }
        else {
            aReturnValue = Response.notModified("One or both parameters are null.").build();
        }
        return aReturnValue;
    }

    /** {@inheritDoc} */
    @Override
    public Response enqueue(final List<Aircraft> theAircraftList) {
        Response aReturnValue;

        if (CollectionUtils.isEmpty(theAircraftList)) {
            aReturnValue = Response.notModified("List of aircraft is null or empty.").build();
        }
        else {
            try {
                this.aqmProcessor.enqueue(theAircraftList);
                aReturnValue = Response.ok("Bulk enqueue of aircraft is complete.").build();
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
                aReturnValue = Response.ok("There are no aircraft in the system.").build();
            }
            else {
                aReturnValue = Response.ok(
                                   String.format("%s %s aircraft (%s) removed.", anAircraft.getSize(), anAircraft.getType(), anAircraft.getUuid())
                               ).build();
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

package com.khalichi.aqm.service.resource;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.khalichi.aqm.data.Aircraft;
import com.khalichi.aqm.manager.AQMProcessor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static com.khalichi.aqm.data.Aircraft.AircraftSize;
import static com.khalichi.aqm.data.Aircraft.AircraftType;

/**
 * REST resource for {@link com.khalichi.aqm.manager.AQMProcessor} operations.  Incorporates Swagger REST API documentation.
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@Path("/aqm")
@Produces(MediaType.APPLICATION_JSON)
@Api(description="APIs for ATC Queue Processor")
public interface AQMResource {

    /** @see AQMProcessor#boot() */
    @POST
    @Path("/boot")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Bootstrap system.")
    Response boot();

    /** @see AQMProcessor#enqueue(Aircraft) */
    @POST
    @Path("/enq")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Enqueue an aircraft into the system.")
    Response enqueue(@NotNull @ApiParam(name = "type", value = "Aircraft Type", type = "enum") @QueryParam("type") AircraftType theAircraftType,
                     @NotNull @ApiParam(name = "size", value = "Aircraft Size", type = "enum") @QueryParam("size") AircraftSize theAircraftSize);

    /** @see AQMProcessor#enqueue(List) */
    @POST
    @Path("/enq/bulk")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Enqueue aircrafts into the system, in bulk.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aircraftList", value = "List of Aircraft", dataType = "java.util.List", required = true, paramType = "body")
    })
    Response enqueue(@NotNull List<Aircraft> theAircraftList);

    /** @see AQMProcessor#dequeue() */
    @DELETE
    @Path("/deq")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Dequeue an aircraft from the system.")
    Response dequeue();

    /** @see AQMProcessor#dump() */
    @GET
    @Path("/dump")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "List aircraft in system; no ordering.")
    Response dump();

    /** @see AQMProcessor#sortDump() */
    @GET
    @Path("/dump/sort")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "List aircraft in system; sorted ordering.")
    Response sortDump();
}

package com.khalichi.atcq.service.resource;

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
import com.khalichi.atcq.data.Aircraft;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static com.khalichi.atcq.data.Aircraft.AircraftSize;
import static com.khalichi.atcq.data.Aircraft.AircraftType;

/**
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@Path("/atcq")
@Produces(MediaType.APPLICATION_JSON)
@Api(description="APIs for ATC Queue Processor")
public interface ATCQueueResource {
    @POST
    @Path("/boot")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Bootstrap system.")
    Response boot();

    @POST
    @Path("/enq")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Enqueue an aircraft into the system.")
    Response enqueue(@NotNull @ApiParam(name = "type", value = "Aircraft Type", type = "enum") @QueryParam("type") AircraftType theAircraftType,
                     @NotNull @ApiParam(name = "size", value = "Aircraft Size", type = "enum") @QueryParam("size") AircraftSize theAircraftSize);

    @POST
    @Path("/enq/bulk")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Enqueue aircrafts into the system, in bulk.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aircrafts", value = "List of Aircrafts", dataType = "java.util.List", required = true, paramType = "body")
    })
    Response enqueue(@NotNull List<Aircraft> theAircraftList);

    @DELETE
    @Path("/deq")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Dequeue an aircraft from the system.")
    Response dequeue();

    @GET
    @Path("/dump")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "List aircraft in system; no ordering.")
    Response dump();

    @GET
    @Path("/dump/sort")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "List aircraft in system; sorted ordering.")
    Response sortDump();
}

package com.khalichi.atcq.service.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@Path("/partner")
@Produces(MediaType.APPLICATION_JSON)
@Api(description="APIs ATC Queue Processor")
public interface ATCQueueResource {
    @GET
    @Path("/atcq")
    @ApiOperation("Bootstrap system.")
    Response boot();

}

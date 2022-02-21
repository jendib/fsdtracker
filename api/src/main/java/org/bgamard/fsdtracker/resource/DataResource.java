package org.bgamard.fsdtracker.resource;

import org.bgamard.fsdtracker.dto.*;
import org.bgamard.fsdtracker.service.TripService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DataResource {
    @Inject
    TripService tripService;

    @GET
    @Path("byDate")
    public List<TripDateData> byDate(
            @QueryParam("condition") TripCondition condition,
            @QueryParam("type") TripType type) {
        if (type == null) {
            throw new BadRequestException();
        }
        return tripService.dateQuery(condition, type);
    }

    @GET
    @Path("byVersion")
    public List<TripVersionData> byVersion(
            @QueryParam("condition") TripCondition condition,
            @QueryParam("type") TripType type) {
        if (type == null) {
            throw new BadRequestException();
        }
        return tripService.versionQuery(condition, type);
    }

    @GET
    @Path("count")
    public CountData count() {
        return tripService.count();
    }
}
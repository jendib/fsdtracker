package org.bgamard.fsdtracker.resource;

import org.bgamard.fsdtracker.dto.CountData;
import org.bgamard.fsdtracker.dto.TripCondition;
import org.bgamard.fsdtracker.dto.TripDateData;
import org.bgamard.fsdtracker.dto.TripType;
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
    public List<TripDateData> list(
            @QueryParam("condition") TripCondition condition,
            @QueryParam("type") TripType type) {
        if (type == null) {
            throw new BadRequestException();
        }
        return tripService.dateQuery(condition, type);
    }

    @GET
    @Path("count")
    public CountData count() {
        return tripService.count();
    }
}
package org.bgamard.fsdtracker.resource;

import io.quarkus.panache.common.Sort;
import org.bgamard.fsdtracker.dto.TripRequest;
import org.bgamard.fsdtracker.entity.Role;
import org.bgamard.fsdtracker.entity.Trip;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/trip")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({Role.Names.MEMBER, Role.Names.ADMIN })
public class TripResource {
    @GET
    public List<Trip> list() {
        return Trip.listAll(Sort.by("date").descending());
    }

    @POST
    @Transactional
    public Trip create(@Valid TripRequest form) {
        Trip trip = form.toEntity();
        trip.id = null;
        trip.persist();
        return trip;
    }

    @PUT
    @Transactional
    public void update(@Valid TripRequest form) {
        Trip trip = Trip.findById(form.id);
        if (trip == null) {
            throw new NotFoundException();
        }
        form.copyTo(trip);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") UUID id) {
        if (!Trip.deleteById(id)) {
            throw new NotFoundException();
        }
        return Response.ok().build();
    }
}
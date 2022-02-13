package org.bgamard.fsdtracker.resource;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import org.bgamard.fsdtracker.dto.TripRequest;
import org.bgamard.fsdtracker.entity.Role;
import org.bgamard.fsdtracker.entity.Trip;
import org.bgamard.fsdtracker.entity.User;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
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
    @Inject
    JsonWebToken jwt;

    @GET
    public List<Trip> list() {
        return Trip.list("user = :user",
                Sort.by("date").descending().and("id").ascending(),
                Parameters.with("user", User.fromJwt(jwt)));
    }

    @POST
    @Transactional
    public Trip create(@Valid TripRequest form) {
        Trip trip = form.toEntity();
        trip.id = null;
        trip.user = User.fromJwt(jwt);
        trip.persist();
        return trip;
    }

    @PUT
    @Transactional
    public void update(@Valid TripRequest form) {
        Trip trip = Trip.findById(form.id);
        if (trip == null || !trip.user.id.equals(User.fromJwt(jwt).id)) {
            throw new NotFoundException();
        }
        form.copyTo(trip);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") UUID id) {
        Trip trip = Trip.findById(id);
        if (trip == null || !trip.user.id.equals(User.fromJwt(jwt).id)) {
            throw new NotFoundException();
        }

        trip.delete();
        return Response.ok().build();
    }
}
package org.bgamard.fsdtracker.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.panache.common.Parameters;
import io.smallrye.jwt.build.Jwt;
import org.bgamard.fsdtracker.dto.UserLoginRequest;
import org.bgamard.fsdtracker.dto.UserLoginResponse;
import org.bgamard.fsdtracker.dto.UserMeResponse;
import org.bgamard.fsdtracker.entity.User;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    JsonWebToken jwt;

    @GET
    @Path("me")
    public UserMeResponse me() {
        UserMeResponse response = new UserMeResponse();
        response.anonymous = jwt.getName() == null;
        if (jwt.getName() != null) {
            response.name = jwt.getName();
            response.roles = jwt.getGroups();
        }
        return response;
    }

    @POST
    @Path("login")
    public UserLoginResponse login(UserLoginRequest request) {
        User user = User.find("name = :name",
                Parameters.with("name", request.name)).firstResult();
        if (user == null) {
            throw new ForbiddenException();
        }

        if (!BcryptUtil.matches(request.password, user.password)) {
            throw new ForbiddenException();
        }

        UserLoginResponse response = new UserLoginResponse();
        response.token = Jwt.upn(user.name)
                .subject(user.id.toString())
                .groups(user.role.toString())
                .expiresIn(Duration.ofDays(30))
                .sign();
        return response;
    }
}
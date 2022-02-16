package org.bgamard.fsdtracker.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.bgamard.fsdtracker.dto.UserLoginRequest;
import org.bgamard.fsdtracker.dto.UserLoginResponse;
import org.bgamard.fsdtracker.dto.UserMeResponse;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class UserResourceTest {
    @Test
    public void testAuth() {
        UserLoginRequest request = new UserLoginRequest();
        request.name = "jendib";
        request.password = "password";
        UserLoginResponse response = given()
                .when().body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .post("/user/login")
                .then()
                .statusCode(200)
                .extract().body().as(UserLoginResponse.class);
        assertNotNull(response.token);

        UserMeResponse userMeResponse = given()
                .when().header("Authorization", "Bearer " + response.token)
                .get("/user/me")
                .then()
                .statusCode(200)
                .extract().body().as(UserMeResponse.class);
        assertEquals(request.name, userMeResponse.name);
    }
}
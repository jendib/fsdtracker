package org.bgamard.fsdtracker.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.bgamard.fsdtracker.dto.TripCondition;
import org.bgamard.fsdtracker.dto.TripRequest;
import org.bgamard.fsdtracker.entity.Trip;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.bgamard.fsdtracker.util.TestConstants.AUTHORIZATION;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class TripResourceTest {
    @Test
    public void testCrud() {
        given()
                .when()
                .header("Authorization", AUTHORIZATION)
                .get("/trip")
                .then()
                .statusCode(200)
                .extract().body().as(Trip[].class);

        TripRequest form = new TripRequest();
        form.date = LocalDate.now();
        form.location = "San Diego";
        form.condition = TripCondition.DAY;
        form.version = "10.10";
        Trip trip = given()
                .when()
                .header("Authorization", AUTHORIZATION)
                .body(form)
                .contentType(MediaType.APPLICATION_JSON)
                .post("/trip")
                .then()
                .statusCode(200)
                .extract().body().as(Trip.class);
        assertNotNull(trip.id);

        Trip[] trips = given()
                .when()
                .header("Authorization", AUTHORIZATION)
                .get("/trip")
                .then()
                .statusCode(200)
                .extract().body().as(Trip[].class);
        assertTrue(trips.length > 0);

        given()
                .when()
                .header("Authorization", AUTHORIZATION)
                .delete("/trip/" + trip.id)
                .then()
                .statusCode(200);
    }
}
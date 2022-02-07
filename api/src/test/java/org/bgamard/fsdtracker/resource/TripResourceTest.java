package org.bgamard.fsdtracker.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.bgamard.fsdtracker.dto.TripForm;
import org.bgamard.fsdtracker.entity.Trip;
import org.bgamard.fsdtracker.entity.TripCondition;
import org.bgamard.fsdtracker.entity.TripLocation;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class TripResourceTest {
    @Test
    public void testCrud() {
        given()
                .when().get("/trip")
                .then()
                .statusCode(200)
                .extract().body().as(Trip[].class);

        TripForm form = new TripForm();
        form.date = LocalDate.now();
        form.location = TripLocation.SAN_DIEGO;
        form.condition = TripCondition.DAY;
        Trip trip = given()
                .when().body(form)
                .contentType(MediaType.APPLICATION_JSON)
                .post("/trip")
                .then()
                .statusCode(200)
                .extract().body().as(Trip.class);
        assertNotNull(trip.id);

        Trip[] trips = given()
                .when().get("/trip")
                .then()
                .statusCode(200)
                .extract().body().as(Trip[].class);
        assertEquals(1, trips.length);

        given()
                .when().delete("/trip/" + trip.id)
                .then()
                .statusCode(200);
    }
}
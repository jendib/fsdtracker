package org.bgamard.fsdtracker.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.bgamard.fsdtracker.dto.*;
import org.bgamard.fsdtracker.entity.Trip;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.bgamard.fsdtracker.util.TestConstants.AUTHORIZATION;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class DataResourceTest {
    @Test
    public void test() {
        var form = new TripRequest();
        form.date = LocalDate.now();
        form.location = "San Diego";
        form.condition = TripCondition.DAY;
        form.version = "10.10";
        form.duration = 5;
        form.streetDistance = 60;
        form.streetCriticalFailure = 2;
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

        var dataDates = given()
                .when()
                .queryParam("type", TripType.STREET)
                .get("/data/byDate")
                .then()
                .statusCode(200)
                .extract().body().as(TripDateData[].class);
        assertTrue(dataDates.length > 0);

        dataDates = given()
                .when()
                .queryParam("type", TripType.STREET)
                .queryParam("condition", TripCondition.DAY)
                .get("/data/byDate")
                .then()
                .statusCode(200)
                .extract().body().as(TripDateData[].class);
        assertTrue(dataDates.length > 0);

        var dataVersions = given()
                .when()
                .queryParam("type", TripType.STREET)
                .get("/data/byVersion")
                .then()
                .statusCode(200)
                .extract().body().as(TripVersionData[].class);
        assertTrue(dataVersions.length > 0);

        dataVersions = given()
                .when()
                .queryParam("type", TripType.STREET)
                .queryParam("condition", TripCondition.DAY)
                .get("/data/byVersion")
                .then()
                .statusCode(200)
                .extract().body().as(TripVersionData[].class);
        assertTrue(dataVersions.length > 0);

        var countData = given()
                .when()
                .get("/data/count")
                .then()
                .statusCode(200)
                .extract().body().as(CountData.class);
        assertTrue(countData.distance > 0);
    }
}
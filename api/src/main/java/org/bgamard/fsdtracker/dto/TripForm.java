package org.bgamard.fsdtracker.dto;

import org.bgamard.fsdtracker.entity.Trip;
import org.bgamard.fsdtracker.entity.TripCondition;
import org.bgamard.fsdtracker.entity.TripLocation;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class TripForm {
    public UUID id;

    @NotNull
    public LocalDate date;

    public int duration;

    public double highwayDistance;

    public double streetDistance;

    public int simpleFailure;

    public int criticalFailure;

    @NotNull
    public TripLocation location;

    @NotNull
    public TripCondition condition;

    public Trip toEntity() {
        Trip trip = new Trip();
        copyTo(trip);
        return trip;
    }

    public void copyTo(Trip trip) {
        trip.id = id;
        trip.date = date;
        trip.duration = duration;
        trip.highwayDistance = highwayDistance;
        trip.streetDistance = streetDistance;
        trip.simpleFailure = simpleFailure;
        trip.criticalFailure = criticalFailure;
        trip.condition = condition;
        trip.location = location;
    }
}

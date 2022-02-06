package org.bgamard.fsdtracker.dto;

import org.bgamard.fsdtracker.entity.Trip;
import org.bgamard.fsdtracker.entity.TripLocation;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class TripForm {
    public UUID id;

    @NotNull
    public LocalDateTime date;

    public int duration;

    public double highwayDistance;

    public double streetDistance;

    public int simpleFailure;

    public int criticalFailure;

    @Enumerated(EnumType.STRING)
    @NotNull
    public TripLocation location;

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
        trip.location = location;
    }
}

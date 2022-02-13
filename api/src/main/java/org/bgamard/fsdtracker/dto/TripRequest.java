package org.bgamard.fsdtracker.dto;

import org.bgamard.fsdtracker.entity.Trip;
import org.bgamard.fsdtracker.entity.TripCondition;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class TripRequest {
    public UUID id;

    @NotNull
    public LocalDate date;

    public int duration;

    public double highwayDistance;

    public double streetDistance;

    public int streetSimpleFailure;

    public int streetCriticalFailure;

    public int highwaySimpleFailure;

    public int highwayCriticalFailure;

    public int throttleIntervention;

    public int cancellationIntervention;

    @NotNull
    public String location;

    @NotNull
    public TripCondition condition;

    public String version;

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
        trip.highwaySimpleFailure = highwaySimpleFailure;
        trip.highwayCriticalFailure = highwayCriticalFailure;
        trip.streetSimpleFailure = streetSimpleFailure;
        trip.streetCriticalFailure = streetCriticalFailure;
        trip.throttleIntervention = throttleIntervention;
        trip.cancellationIntervention = cancellationIntervention;
        trip.condition = condition;
        trip.location = location;
        trip.version = version;
    }
}

package org.bgamard.fsdtracker.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
public class Trip extends BaseEntity {
    public LocalDateTime date;

    public int duration;

    public double highwayDistance;

    public double streetDistance;

    public int simpleFailure;

    public int criticalFailure;

    @Enumerated(EnumType.STRING)
    public TripLocation location; // TODO Use postgis location type?
}

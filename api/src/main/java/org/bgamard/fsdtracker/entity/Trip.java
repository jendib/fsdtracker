package org.bgamard.fsdtracker.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
public class Trip extends BaseEntity {
    public LocalDate date;

    public int duration;

    public double highwayDistance;

    public double streetDistance;

    public int simpleFailure;

    public int criticalFailure;

    @Enumerated(EnumType.STRING)
    public TripCondition condition;

    public String location;

    public String version;
}

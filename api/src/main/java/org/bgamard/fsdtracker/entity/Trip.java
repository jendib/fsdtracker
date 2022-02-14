package org.bgamard.fsdtracker.entity;

import org.bgamard.fsdtracker.dto.TripCondition;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Trip extends BaseEntity {
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

    @Enumerated(EnumType.STRING)
    public TripCondition condition;

    public String location;

    public String version;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    public User user;
}

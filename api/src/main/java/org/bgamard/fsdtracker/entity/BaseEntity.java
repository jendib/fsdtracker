package org.bgamard.fsdtracker.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "updatableUUIDGenerator")
    @GenericGenerator(name="updatableUUIDGenerator", strategy = "org.bgamard.fsdtracker.util.UpdatableUUIDGenerator")
    @Column(nullable = false)
    public UUID id;

    public BaseEntity() {
    }

    public String toString() {
        return this.getClass().getSimpleName() + "<" + this.id + ">";
    }
}
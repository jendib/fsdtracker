package org.bgamard.fsdtracker.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "fuser")
public class User extends BaseEntity {
    public String name;

    public String password;

    @Enumerated(EnumType.STRING)
    public Role role;
}

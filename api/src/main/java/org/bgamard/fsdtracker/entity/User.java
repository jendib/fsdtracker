package org.bgamard.fsdtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "fuser")
public class User extends BaseEntity {
    public String name;

    @JsonIgnore
    public String password;

    @Enumerated(EnumType.STRING)
    public Role role;

    public static User fromJwt(JsonWebToken jwt) {
        return User.findById(UUID.fromString(jwt.getSubject()));
    }
}

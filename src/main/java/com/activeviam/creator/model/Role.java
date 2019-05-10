package com.activeviam.creator.model;

import javax.persistence.*;
import java.util.Set;

@Entity
//@Table(name = "role" )
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Developper> developpers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Developper> getUsers() {
        return developpers;
    }

    public void setUsers(Set<Developper> developpers) {
        this.developpers = developpers;
    }
}

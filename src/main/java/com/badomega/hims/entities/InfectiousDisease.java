package com.badomega.hims.entities;

import com.badomega.hims.enums.Role;
import com.badomega.hims.enums.SpreadRisk;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "infectious_diseases")
public class InfectiousDisease {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private SpreadRisk spreadRisk;

    @ManyToMany(mappedBy = "diseases")
    public Set<Person> people;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpreadRisk getSpreadRisk() {
        return spreadRisk;
    }

    public void setSpreadRisk(SpreadRisk spreadRisk) {
        this.spreadRisk = spreadRisk;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }
}

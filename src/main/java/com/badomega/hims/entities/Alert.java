package com.badomega.hims.entities;

import com.badomega.hims.enums.AlertType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer ruleBroken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertType type;

    @ManyToOne(optional = false)
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name="other_person_id")
    private Person otherPerson;

    @ManyToOne
    @JoinColumn(name="beacon_id")
    private Beacon beacon;


    public Integer getId() {
        return id;
    }

    public Integer getRuleBroken() {
        return ruleBroken;
    }

    public void setRuleBroken(Integer ruleBroken) {
        this.ruleBroken = ruleBroken;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getOtherPerson() {
        return otherPerson;
    }

    public void setOtherPerson(Person otherPerson) {
        this.otherPerson = otherPerson;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}

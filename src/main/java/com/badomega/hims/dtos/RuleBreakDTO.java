package com.badomega.hims.dtos;

import com.badomega.hims.entities.Beacon;
import com.badomega.hims.entities.Person;

import java.util.Date;

/**
 * Created by azura on 18.11.2016.
 */
public class RuleBreakDTO {
    private Date date;
    private Person person;
    private Person otherPerson;
    private Beacon beacon;
    private String message;

    public RuleBreakDTO(Date date, Person person, Person otherPerson, Beacon beacon, String message) {
        this.date = date;
        this.person = person;
        this.otherPerson = otherPerson;
        this.beacon = beacon;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

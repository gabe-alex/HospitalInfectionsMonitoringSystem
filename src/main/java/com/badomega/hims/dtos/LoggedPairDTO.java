package com.badomega.hims.dtos;

import com.badomega.hims.entities.Person;

import java.util.Date;

public class LoggedPairDTO {
    Person selfPerson;
    Person otherPerson;
    Date interactionDate;

    public LoggedPairDTO(Person selfPerson, Person otherPerson, Date interactionDate) {
        this.selfPerson = selfPerson;
        this.otherPerson = otherPerson;
        this.interactionDate = interactionDate;
    }

    public Person getSelfPerson() {
        return selfPerson;
    }

    public void setSelfPerson(Person selfPerson) {
        this.selfPerson = selfPerson;
    }

    public Person getOtherPerson() {
        return otherPerson;
    }

    public void setOtherPerson(Person otherPerson) {
        this.otherPerson = otherPerson;
    }

    public Date getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(Date interactionDate) {
        this.interactionDate = interactionDate;
    }
}

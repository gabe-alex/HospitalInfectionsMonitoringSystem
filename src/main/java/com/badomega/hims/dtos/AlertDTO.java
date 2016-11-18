package com.badomega.hims.dtos;

import com.badomega.hims.entities.Disease;
import com.badomega.hims.entities.Person;

import java.util.List;

/**
 * Created by azura on 18.11.2016.
 */
public class AlertDTO {
    private Person selfPerson;
    private Person targetPerson;
    private List<Disease> selfToTargetDiseases;
    private List<Disease> targetToSelfDiseases;

    public AlertDTO(Person selfPerson, Person targetPerson, List<Disease> selfToTargetDiseases, List<Disease> targetToSelfDiseases) {
        this.selfPerson = selfPerson;
        this.targetPerson = targetPerson;
        this.selfToTargetDiseases = selfToTargetDiseases;
        this.targetToSelfDiseases = targetToSelfDiseases;
    }

    public Person getSelfPerson() {
        return selfPerson;
    }

    public void setSelfPerson(Person selfPerson) {
        this.selfPerson = selfPerson;
    }

    public Person getTargetPerson() {
        return targetPerson;
    }

    public void setTargetPerson(Person targetPerson) {
        this.targetPerson = targetPerson;
    }

    public List<Disease> getSelfToTargetDiseases() {
        return selfToTargetDiseases;
    }

    public void setSelfToTargetDiseases(List<Disease> selfToTargetDiseases) {
        this.selfToTargetDiseases = selfToTargetDiseases;
    }

    public List<Disease> getTargetToSelfDiseases() {
        return targetToSelfDiseases;
    }

    public void setTargetToSelfDiseases(List<Disease> targetToSelfDiseases) {
        this.targetToSelfDiseases = targetToSelfDiseases;
    }
}

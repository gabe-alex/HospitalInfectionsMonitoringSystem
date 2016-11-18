package com.badomega.hims.dtos;

import com.badomega.hims.entities.Disease;
import com.badomega.hims.entities.Interaction;
import com.badomega.hims.entities.Person;

import java.util.List;

/**
 * Created by azura on 18.11.2016.
 */
public class AlertDTO {
    private Interaction interaction;
    private List<Disease> selfToTargetDiseases;
    private List<Disease> targetToSelfDiseases;

    public AlertDTO(Interaction interaction, List<Disease> selfToTargetDiseases, List<Disease> targetToSelfDiseases) {
        this.interaction = interaction;
        this.selfToTargetDiseases = selfToTargetDiseases;
        this.targetToSelfDiseases = targetToSelfDiseases;
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

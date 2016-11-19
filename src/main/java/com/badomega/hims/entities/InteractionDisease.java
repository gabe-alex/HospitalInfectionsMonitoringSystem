package com.badomega.hims.entities;

import javax.persistence.*;

@Entity
@Table(name = "interaction_diseases")
public class InteractionDisease {
    @Id
    private Integer id;

    @ManyToOne
    private Interaction interaction;

    @ManyToOne
    private Disease disease;

    @Column
    private Boolean fromTarget;

    public Integer getId() {
        return id;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Boolean getFromTarget() {
        return fromTarget;
    }

    public void setFromTarget(Boolean fromTarget) {
        this.fromTarget = fromTarget;
    }
}

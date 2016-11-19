package com.badomega.hims.entities;

import com.badomega.hims.enums.AlertType;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "interactions")
public class Interaction {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "self_mac_address")
    private String selfMacAddress;

    @Column(name = "target_mac_address")
    private String targetMacAddress;

    @ManyToOne
    @JoinColumn(name = "self_mac_address", referencedColumnName = "phone_mac_address", insertable = false, updatable = false)
    private Person self;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToOne
    @JoinColumn(name = "target_mac_address", referencedColumnName = "phone_mac_address", insertable = false, updatable = false)
    private Person targetPerson;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToOne
    @JoinColumn(name = "target_mac_address", referencedColumnName = "mac_address", insertable = false, updatable = false)
    private Beacon targetBeacon;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTimestamp;

    @Column
    private Float minDistance;

    @OneToMany(mappedBy = "interaction")
    private Set<InteractionDisease> interactionDiseases;

    public Integer getId() {
        return id;
    }

    public String getSelfMacAddress() {
        return selfMacAddress;
    }

    public void setSelfMacAddress(String selfMacAddress) {
        this.selfMacAddress = selfMacAddress;
    }

    public String getTargetMacAddress() {
        return targetMacAddress;
    }

    public void setTargetMacAddress(String targetMacAddress) {
        this.targetMacAddress = targetMacAddress;
    }

    public Person getSelf() {
        return self;
    }

    public void setSelf(Person self) {
        this.self = self;
    }

    public Person getTargetPerson() {
        return targetPerson;
    }

    public void setTargetPerson(Person targetPerson) {
        this.targetPerson = targetPerson;
    }

    public Beacon getTargetBeacon() {
        return targetBeacon;
    }

    public void setTargetBeacon(Beacon targetBeacon) {
        this.targetBeacon = targetBeacon;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Float getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Float minDistance) {
        this.minDistance = minDistance;
    }

    public Set<InteractionDisease> getInteractionDiseases() {
        return interactionDiseases;
    }

    public void setInteractionDiseases(Set<InteractionDisease> interactionDiseases) {
        this.interactionDiseases = interactionDiseases;
    }
}

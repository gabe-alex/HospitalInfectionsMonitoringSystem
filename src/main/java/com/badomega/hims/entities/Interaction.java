package com.badomega.hims.entities;

import com.badomega.hims.enums.AlertType;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "interactions")
public class Interaction {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String selfMacAddress;

    @Column
    private String targetMacAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTimestamp;

    @Column
    private Float minDistance;

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
}

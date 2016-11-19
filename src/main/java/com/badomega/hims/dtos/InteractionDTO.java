package com.badomega.hims.dtos;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class InteractionDTO {
    @NotEmpty
    private String selfMacAddress;

    @NotEmpty
    private String targetMacAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTimestamp;

    private Float minDistance;

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

package com.badomega.hims.entities;

import com.badomega.hims.enums.LocationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "beacons")
public class Beacon implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String mac_address;

    @Enumerated(EnumType.STRING)
    private LocationType location_type;

    @Column(nullable = false)
    private String location_description;

    @JsonIgnore
    @OneToMany(mappedBy = "targetBeacon")
    private Set<Interaction> interactions;


    public Integer getId() {
        return id;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public LocationType getLocation_type() {
        return location_type;
    }

    public void setLocation_type(LocationType location_type) {
        this.location_type = location_type;
    }

    public String getLocation_description() {
        return location_description;
    }

    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }

    public Set<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(Set<Interaction> interactions) {
        this.interactions = interactions;
    }
}

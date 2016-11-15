package com.badomega.hims.entities;

import com.badomega.hims.enums.LocationType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "beacons")
public class Beacon {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String mac_address;

    @Enumerated(EnumType.STRING)
    private LocationType location_type;

    @Column(nullable = false)
    private String location_desription;

    @OneToMany(mappedBy="beacon")
    private Set<Alert> alerts;


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

    public String getLocation_desription() {
        return location_desription;
    }

    public void setLocation_desription(String location_desription) {
        this.location_desription = location_desription;
    }

    public Set<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(Set<Alert> alerts) {
        this.alerts = alerts;
    }
}

package com.badomega.hims.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "beacons")
public class Beacon {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy="beacon")
    private Set<Alert> alerts;


    public Integer getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(Set<Alert> alerts) {
        this.alerts = alerts;
    }
}

package com.badomega.hims.entities;

import com.badomega.hims.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "person_disease", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id", referencedColumnName = "id"))
    private Set<InfectiousDisease> diseases;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<InfectiousDisease> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<InfectiousDisease> diseases) {
        this.diseases = diseases;
    }
}

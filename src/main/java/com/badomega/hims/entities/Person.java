package com.badomega.hims.entities;

import com.badomega.hims.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "people")
public class Person implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String phone_mac_address;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy="person")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "person_disease", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id", referencedColumnName = "id"))
    private Set<Disease> diseases;

    @JsonIgnore
    @OneToMany(mappedBy = "selfPerson")
    private Set<Interaction> interactionsFrom;

    @JsonIgnore
    @OneToMany(mappedBy = "targetPerson")
    private Set<Interaction> interactionsTo;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhone_mac_address() {
        return phone_mac_address;
    }

    public void setPhone_mac_address(String phone_mac_address) {
        this.phone_mac_address = phone_mac_address;
    }

    public Set<Interaction> getInteractionsFrom() {
        return interactionsFrom;
    }

    public void setInteractionsFrom(Set<Interaction> interactionsFrom) {
        this.interactionsFrom = interactionsFrom;
    }

    public Set<Interaction> getInteractionsTo() {
        return interactionsTo;
    }

    public void setInteractionsTo(Set<Interaction> interactionsTo) {
        this.interactionsTo = interactionsTo;
    }
}

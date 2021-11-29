package com.hiapoe.geopositionservice.entities;

import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class TrackedEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Column(unique = true)
    private String name;
    private String description;

    public TrackedEntity() {
    }

    @Override
    public String toString() {
        return "TrackedEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

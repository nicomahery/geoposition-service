package com.hiapoe.geopositionservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Location implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(columnDefinition = "POINT")
    private Point coordinates;
    @ManyToOne()
    @NotNull
    private TrackedEntity trackedEntity;

    public Location() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public TrackedEntity getTrackedEntity() {
        return trackedEntity;
    }

    public void setTrackedEntity(TrackedEntity trackedEntity) {
        this.trackedEntity = trackedEntity;
    }
}

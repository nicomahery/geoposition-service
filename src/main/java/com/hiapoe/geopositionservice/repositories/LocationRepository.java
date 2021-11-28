package com.hiapoe.geopositionservice.repositories;

import com.hiapoe.geopositionservice.entities.Location;
import com.hiapoe.geopositionservice.entities.TrackedEntity;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByTrackedEntity(TrackedEntity trackedEntity);
    @Query(value = "select l from #{#entityName} l where ST_Within(l.coordinates, :area) = TRUE")
    List<Location> findAllInArea(Geometry area);
    Optional<Location> findTopByTrackedEntityOrderByDateDesc(TrackedEntity trackedEntity);
}

package com.hiapoe.geopositionservice.repositories;

import com.hiapoe.geopositionservice.entities.TrackedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackedEntityRepository extends JpaRepository<TrackedEntity, Long> {
    Optional<TrackedEntity> findByName(String name);

}

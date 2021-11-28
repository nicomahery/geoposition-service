package com.hiapoe.geopositionservice.services;

import com.hiapoe.geopositionservice.entities.TrackedEntity;
import com.hiapoe.geopositionservice.repositories.TrackedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackedEntityService {

    final private TrackedEntityRepository trackedEntityRepository;

    @Autowired
    public TrackedEntityService(TrackedEntityRepository trackedEntityRepository) {
        this.trackedEntityRepository = trackedEntityRepository;
    }

    public Optional<TrackedEntity> findByName(String name) {
        return this.trackedEntityRepository.findByName(name);
    }

    public Optional<TrackedEntity> findById(long id) {
        return this.trackedEntityRepository.findById(id);
    }

    public TrackedEntity save(TrackedEntity trackedEntity) {
        return this.trackedEntityRepository.save(trackedEntity);
    }

    public List<TrackedEntity> saveAll(Iterable<TrackedEntity> trackedEntityIterable) {
        return this.trackedEntityRepository.saveAll(trackedEntityIterable);
    }
}

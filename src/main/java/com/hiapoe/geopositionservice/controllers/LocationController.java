package com.hiapoe.geopositionservice.controllers;

import com.hiapoe.geopositionservice.entities.Location;
import com.hiapoe.geopositionservice.entities.TrackedEntity;
import com.hiapoe.geopositionservice.hateoas.LocationModelAssembler;
import com.hiapoe.geopositionservice.services.LocationService;
import com.hiapoe.geopositionservice.services.TrackedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/custom-api/locations")
public class LocationController {

    final private LocationService locationService;
    final private TrackedEntityService trackedEntityService;
    final private LocationModelAssembler locationModelAssembler;

    @Autowired
    public LocationController(LocationService locationService, TrackedEntityService trackedEntityService,
                              LocationModelAssembler locationModelAssembler) {
        this.locationService = locationService;
        this.trackedEntityService = trackedEntityService;
        this.locationModelAssembler = locationModelAssembler;
    }

    @GetMapping(value = "/getLastLocationByTrackedEntityName/{name}")
    public ResponseEntity<?> getLastLocationByTrackedEntityName(@PathVariable String name) {
        Optional<TrackedEntity> optionalTrackedEntity = this.trackedEntityService.findByName(name);
        if (optionalTrackedEntity.isPresent()) {
            return ResponseEntity.ok(
                    this.locationService.findTopByTrackedEntityOrderByDateDesc(optionalTrackedEntity.get()));
        }
        else {
            return new ResponseEntity<>("No TrackedEntity for name: " + name, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getAllByTrackedEntityName/{name}")
    public ResponseEntity<?> getAllByTrackedEntityName(@PathVariable String name) {
        Optional<TrackedEntity> optionalTrackedEntity = this.trackedEntityService.findByName(name);
        if (optionalTrackedEntity.isPresent()) {
            return ResponseEntity.ok(
                    this.locationService.findAllByTrackedEntity(optionalTrackedEntity.get()));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No TrackedEntity for name: " + name);
        }
    }

    @GetMapping(value = "/getAllLocationAroundPoint/{latitude}/{longitude}/{diameterInMeters}")
    public CollectionModel<EntityModel<Location>> getAllLocationAroundPoint(@PathVariable double latitude,
                                                       @PathVariable double longitude,
                                                       @PathVariable double diameterInMeters) {
        List<EntityModel<Location>> locations
                = this.locationService.findAllAroundCoordinates(latitude, longitude, diameterInMeters)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());
        return CollectionModel.of(locations);
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<Location>> getAll(HttpServletRequest request) {
        List<EntityModel<Location>> locations
                = this.locationService.findAll()
                .stream()
                .map(location -> this.locationModelAssembler.toModel(location, request.getRequestURL().toString()))
                .collect(Collectors.toList());
        return CollectionModel.of(locations);
    }
}

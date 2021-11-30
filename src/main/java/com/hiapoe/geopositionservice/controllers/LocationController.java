package com.hiapoe.geopositionservice.controllers;

import com.hiapoe.geopositionservice.entities.Location;
import com.hiapoe.geopositionservice.entities.TrackedEntity;
import com.hiapoe.geopositionservice.hateoas.LocationModelAssembler;
import com.hiapoe.geopositionservice.services.LocationService;
import com.hiapoe.geopositionservice.services.TrackedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
    public ResponseEntity<?> getLastLocationByTrackedEntityName(@PathVariable String name,
                                                                HttpServletRequest request) {
        Optional<TrackedEntity> optionalTrackedEntity = this.trackedEntityService.findByName(name);
        if (optionalTrackedEntity.isPresent()) {
            Optional<Location> optionalLocation
                    = this.locationService.findTopByTrackedEntityOrderByDateDesc(optionalTrackedEntity.get());
            if (optionalLocation.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("{}");
            }
            return ResponseEntity.ok(
                    this.locationModelAssembler.toModel(optionalLocation.get(), request.getRequestURL().toString()));
        }
        else {
            return new ResponseEntity<>("No TrackedEntity for name: " + name, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getAllByTrackedEntityName/{name}")
    public ResponseEntity<?> getAllByTrackedEntityName(@PathVariable String name,
                                                       HttpServletRequest request) {
        Optional<TrackedEntity> optionalTrackedEntity = this.trackedEntityService.findByName(name);
        if (optionalTrackedEntity.isPresent()) {
            return ResponseEntity.ok(
                    this.locationModelAssembler.toCollectionModel(
                            this.locationService.findAllByTrackedEntity(optionalTrackedEntity.get()),
                            request.getRequestURL().toString()));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No TrackedEntity for name: " + name);
        }
    }

    @GetMapping(value = "/getAllLocationAroundPoint/{longitude}/{latitude}/{diameterInMeters}")
    public CollectionModel<EntityModel<Location>> getAllLocationAroundPoint(@PathVariable double latitude,
                                                       @PathVariable double longitude,
                                                       @PathVariable double diameterInMeters,
                                                        HttpServletRequest request) {
        return this.locationModelAssembler.toCollectionModel(
                this.locationService.findAllAroundCoordinates(latitude, longitude, diameterInMeters),
                request.getRequestURL().toString());
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<Location>> getAll(HttpServletRequest request) {
        return this.locationModelAssembler.toCollectionModel(this.locationService.findAll(),
                request.getRequestURL().toString());
    }
}

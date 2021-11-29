package com.hiapoe.geopositionservice.services;

import com.hiapoe.geopositionservice.entities.Location;
import com.hiapoe.geopositionservice.entities.TrackedEntity;
import com.hiapoe.geopositionservice.repositories.LocationRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    final private LocationRepository locationRepository;
    final static int CIRCLE_POLYGON_POINT_NUMBER = 100;
    final static double LATITUDE_DEGREE_TO_KM = 111320d;
    final static double LONGITUDE_FACTOR_KM = 40075000;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Optional<Location> findById(long id) {
        return this.locationRepository.findById(id);
    }

    private List<Location> findAllInArea(Geometry area) {
        return this.locationRepository.findAllInArea(area);
    }

    public List<Location> findAllAroundCoordinates(double latitude, double longitude, double diameterInMeters) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(CIRCLE_POLYGON_POINT_NUMBER);
        shapeFactory.setCentre(new Coordinate(longitude, latitude));
        shapeFactory.setWidth(diameterInMeters/LATITUDE_DEGREE_TO_KM);
        shapeFactory.setHeight(diameterInMeters / (LONGITUDE_FACTOR_KM * Math.cos(Math.toRadians(latitude)) / 360));
        Polygon circle = shapeFactory.createEllipse();
        return this.findAllInArea(circle);
    }

    public List<Location> findAllByTrackedEntity(TrackedEntity trackedEntity) {
        return this.locationRepository.findAllByTrackedEntity(trackedEntity);
    }

    public Optional<Location> findTopByTrackedEntityOrderByDateDesc(TrackedEntity trackedEntity) {
        return this.locationRepository.findTopByTrackedEntityOrderByDateDesc(trackedEntity);
    }

    public Location save(Location location) {
        return this.locationRepository.save(location);
    }

    public List<Location> saveAll(Iterable<Location> locationIterable) {
        return this.locationRepository.saveAll(locationIterable);
    }

    public List<Location> findAll() {
        return this.locationRepository.findAll();
    }
}

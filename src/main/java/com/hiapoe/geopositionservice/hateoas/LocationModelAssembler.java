package com.hiapoe.geopositionservice.hateoas;

import com.hiapoe.geopositionservice.entities.Location;
import com.hiapoe.geopositionservice.utils.RequestUrlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class LocationModelAssembler implements RepresentationModelAssembler<Location, EntityModel<Location>> {

    final private RequestUrlParser requestUrlParser;

    @Autowired
    public LocationModelAssembler(RequestUrlParser requestUrlParser) {
        this.requestUrlParser = requestUrlParser;
    }

    @NonNull
    @Override
    public EntityModel<Location> toModel(@NonNull Location location) {
        return EntityModel.of(location);
    }

    public EntityModel<Location> toModel(Location location, String url) {
        EntityModel<Location> locationEntityModel = this.toModel(location);
        locationEntityModel.add(Link.of(String.format("%sapi/locations/%d",
                this.requestUrlParser.parseRequestURLToServerLocation(url), location.getId())).withSelfRel());
        locationEntityModel.add(Link.of(String.format("%sapi/locations/%d",
                this.requestUrlParser.parseRequestURLToServerLocation(url), location.getId()), "location"));
        locationEntityModel.add(Link.of(String.format("%sapi/locations/%d/trackedEntity",
                this.requestUrlParser.parseRequestURLToServerLocation(url), location.getId()), "trackedEntity"));
        return locationEntityModel;
    }

    @NonNull
    @Override
    public CollectionModel<EntityModel<Location>> toCollectionModel(@NonNull Iterable<? extends Location> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    public CollectionModel<EntityModel<Location>> toCollectionModel(@NonNull Iterable<? extends Location> entities,
                                                                    String url) {
        Stream<? extends Location> stream = StreamSupport.stream(entities.spliterator(), false);
        CollectionModel<EntityModel<Location>> entityModels
                = CollectionModel.of(stream.map(location -> this.toModel(location, url)).collect(Collectors.toList()));
        entityModels.add(Link.of(String.format("%sapi/locations/",
                this.requestUrlParser.parseRequestURLToServerLocation(url))).withSelfRel());
        entityModels.add(Link.of(String.format("%sapi/profile/locations",
                this.requestUrlParser.parseRequestURLToServerLocation(url)), "profile"));
        entityModels.add(Link.of(String.format("%sapi/search/locations",
                this.requestUrlParser.parseRequestURLToServerLocation(url)), "search"));
        return entityModels;
    }
}

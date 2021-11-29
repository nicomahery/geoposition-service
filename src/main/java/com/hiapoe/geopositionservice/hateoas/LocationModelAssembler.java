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
        return locationEntityModel;
    }
}

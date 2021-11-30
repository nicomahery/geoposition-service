package com.hiapoe.geopositionservice.hateoas;

import com.hiapoe.geopositionservice.entities.Location;
import com.hiapoe.geopositionservice.entities.TrackedEntity;
import com.hiapoe.geopositionservice.utils.RequestUrlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class TrackedEntityModelAssembler implements RepresentationModelAssembler<TrackedEntity, EntityModel<TrackedEntity>> {

    final private RequestUrlParser requestUrlParser;

    @Autowired
    public TrackedEntityModelAssembler(RequestUrlParser requestUrlParser) {
        this.requestUrlParser = requestUrlParser;
    }

    @NonNull
    @Override
    public EntityModel<TrackedEntity> toModel(@NonNull TrackedEntity trackedEntity) {
        return EntityModel.of(trackedEntity);
    }

    public EntityModel<TrackedEntity> toModel(TrackedEntity trackedEntity, String url) {
        EntityModel<TrackedEntity> trackedEntityEntityModel = this.toModel(trackedEntity);
        trackedEntityEntityModel.add(Link.of(String.format("%sapi/trackedEntities/%d",
                this.requestUrlParser.parseRequestURLToServerLocation(url), trackedEntity.getId())).withSelfRel());
        trackedEntityEntityModel.add(Link.of(String.format("%sapi/trackedEntities/%d",
                this.requestUrlParser.parseRequestURLToServerLocation(url), trackedEntity.getId()), "trackedEntity"));
        return trackedEntityEntityModel;
    }

    @NonNull
    @Override
    public CollectionModel<EntityModel<TrackedEntity>> toCollectionModel(@NonNull Iterable<? extends TrackedEntity> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    public CollectionModel<EntityModel<TrackedEntity>> toCollectionModel(@NonNull Iterable<? extends TrackedEntity> entities, String url) {
        Stream<? extends TrackedEntity> stream = StreamSupport.stream(entities.spliterator(), false);
        CollectionModel<EntityModel<TrackedEntity>> entityModels
                = CollectionModel.of(stream.map(trackedEntity -> this.toModel(trackedEntity, url))
                .collect(Collectors.toList()));
        entityModels.add(Link.of(String.format("%sapi/trackedEntities/",
                this.requestUrlParser.parseRequestURLToServerLocation(url))).withSelfRel());
        entityModels.add(Link.of(String.format("%sapi/profile/trackedEntities",
                this.requestUrlParser.parseRequestURLToServerLocation(url)), "profile"));
        entityModels.add(Link.of(String.format("%sapi/search/trackedEntities",
                this.requestUrlParser.parseRequestURLToServerLocation(url)), "search"));
        return entityModels;
    }
}

package com.hiapoe.geopositionservice.controllers;

import com.hiapoe.geopositionservice.entities.TrackedEntity;
import com.hiapoe.geopositionservice.hateoas.TrackedEntityModelAssembler;
import com.hiapoe.geopositionservice.services.TrackedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/custom-api/trackedEntities")
@RestController
public class TrackedEntityController {

    final private TrackedEntityService trackedEntityService;
    final private TrackedEntityModelAssembler trackedEntityModelAssembler;

    @Autowired
    public TrackedEntityController(TrackedEntityService trackedEntityService,
                                   TrackedEntityModelAssembler trackedEntityModelAssembler) {
        this.trackedEntityService = trackedEntityService;
        this.trackedEntityModelAssembler = trackedEntityModelAssembler;
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<TrackedEntity>> findAll(HttpServletRequest request) {
        return this.trackedEntityModelAssembler.toCollectionModel(this.trackedEntityService.findAll(),
                request.getRequestURL().toString());
    }
}

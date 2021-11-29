package com.hiapoe.geopositionservice.controllers;

import com.hiapoe.geopositionservice.entities.TrackedEntity;
import com.hiapoe.geopositionservice.services.TrackedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;

@RequestMapping(value = "/custom-api/trackedEntities")
@RestController
public class TrackedEntityController {

    final private TrackedEntityService trackedEntityService;
    final private ServletContext servletContext;

    @Autowired
    public TrackedEntityController(TrackedEntityService trackedEntityService, ServletContext servletContext) {
        this.trackedEntityService = trackedEntityService;
        this.servletContext = servletContext;
    }


}

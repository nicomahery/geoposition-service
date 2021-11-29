package com.hiapoe.geopositionservice.utils;

import org.springframework.stereotype.Component;

@Component
public class RequestUrlParser {

    final private static String CUSTOM_URL_LOCATION = "custom-api";

    public String parseRequestURLToServerLocation(String requestURL) {
        return requestURL.split(CUSTOM_URL_LOCATION)[0];
    }
}

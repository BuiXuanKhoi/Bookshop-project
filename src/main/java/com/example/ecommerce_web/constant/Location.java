package com.example.ecommerce_web.constant;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;

import java.util.Arrays;

public enum Location {
    HN("HN"),
    HCM("HCM"),
    DN("DN");

    private final String location;

    Location(String location) {
        this.location = location;
    }

    public static Location getLocation(String address){
        return Arrays.stream(values())
                     .filter(locations -> address.equals(locations.location))
                     .findAny()
                     .orElseThrow(
                             () -> new ResourceNotFoundException("Location Not Available !!!"));
    }
}

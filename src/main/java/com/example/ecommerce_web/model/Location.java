package com.example.ecommerce_web.model;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Optional;

public enum Location {
    HN("HN"),
    HCM("HCM"),
    DN("DN");

    private String location;

    Location(String location) {
        this.location = location;
    }

    public static Location getLocation(String address){

        Optional<Location> locationOptional = Arrays.stream(values())
                                                    .filter(locations -> address.equals(locations.location))
                                                    .findAny();

        locationOptional.orElseThrow(
                () -> new ResourceNotFoundException("Location Not Available !!!")
        );

        return locationOptional.get();

    }
}

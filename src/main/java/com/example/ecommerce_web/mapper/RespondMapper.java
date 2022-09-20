package com.example.ecommerce_web.mapper;

public interface RespondMapper<E, R> {

    R toDTO(E model);
}

package com.example.ecommerce_web.mapper;

public interface RequestMapper<T,E> {

    E fromDTO(T requestDTO);
}

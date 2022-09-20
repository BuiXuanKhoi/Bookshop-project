package com.example.ecommerce_web.mapper;

import com.sun.istack.Nullable;

public interface BasicMapper<T, U, O> {

    U toDTO(O entity);

    @Nullable
    O fromDTO(T requestDTO);
}

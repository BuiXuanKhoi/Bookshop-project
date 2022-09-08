package com.example.ecommerce_web.model;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public enum OrderState {
        PREPARED("PREPARED"),
        PACKAGED("PACKAGED"),
        DELIVERED("DELIVERED"),
        RECEIVED("RECEIVED"),
        COMPLETED("COMPLETED");

        private String state;

        OrderState(String state) {
                this.state = state;
        }

        public static OrderState nextState(OrderState orderState){

                switch (orderState)
                {
                        case PREPARED -> {return OrderState.PACKAGED;}
                        case PACKAGED -> {return OrderState.DELIVERED;}
                        case DELIVERED -> {return OrderState.RECEIVED;}
                        case RECEIVED -> {return OrderState.COMPLETED;}
                        case COMPLETED -> throw new ResourceNotFoundException("Completed is the final state !!!");
                        default -> throw new ResourceNotFoundException("State Not Available !!!");
                }
        }
}

package com.example.ecommerce_web.constant;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;

public enum OrderState {
        PREPARED,
        PACKAGED,
        DELIVERED,
        RECEIVED,
        COMPLETED;


        public static OrderState nextOf(OrderState orderState){

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

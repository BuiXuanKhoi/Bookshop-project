package com.example.ecommerce_web.constant;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;

import java.util.Arrays;

public enum BookState {
       AVAILABLE("AVAILABLE"),
       UNAVAILABLE("UNAVAILABLE"),
       OUT_OF_STOCK("OUT_OF_STOCK");

       private final String state;

       BookState(String state) {
              this.state = state;
       }

       public static BookState getState(String bookState)
       {
              return Arrays.stream(values())
                           .filter(states -> bookState.equals(states.state))
                           .findAny()
                           .orElseThrow(
                                   () -> new ResourceNotFoundException("State not available !!!"));
       }
}

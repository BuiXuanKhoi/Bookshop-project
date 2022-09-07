package com.example.ecommerce_web.model;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Optional;

public enum BookState {
       AVAILABLE("AVAILABLE"),
       UNAVAILABLE("UNAVAILABLE"),
       OUT_OF_STOCK("OUT_OF_STOCK"),
       EXPIRED("EXPIRED");

       private String state;

       BookState(String state) {
              this.state = state;
       }

       public static BookState getState(String bookState)
       {

              Optional<BookState> bookStateOptional = Arrays.stream(values())
                                                                .filter(states -> bookState.equals(states.state))
                                                                .findAny();

              bookStateOptional.orElseThrow(
                      () -> new ResourceNotFoundException("State not available !!!")
              );

              return bookStateOptional.get();
       }
}

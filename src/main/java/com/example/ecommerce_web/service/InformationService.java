package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.InformationRespondDTO;
import org.springframework.http.ResponseEntity;

public interface InformationService {
    ResponseEntity<?> modifyInformation(ModifyUserRequestDTO modifyRequestDTO);

    InformationRespondDTO getDetailInformation();
}

package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.InformationRespondDTO;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import org.springframework.http.ResponseEntity;

public interface InformationService {
    ResponseEntity<?> modifyInformation(ModifyUserRequestDTO modifyUserRequestDTO);

    InformationRespondDTO getDetailInformation();

    Information createInformationByExistedUser(UserRequestDTO userRequestDTO, Users users);


}

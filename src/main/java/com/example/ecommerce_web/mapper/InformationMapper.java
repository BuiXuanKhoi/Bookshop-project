package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.InformationRespondDTO;
import com.example.ecommerce_web.model.entities.Information;

public interface InformationMapper extends RespondMapper<Information, InformationRespondDTO> {

    Information toExistedInformation(ModifyUserRequestDTO requestDTO, Information information);

    Information fromUserRequest(UserRequestDTO userRequestDTO);
}

package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.constant.Location;
import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.InformationRespondDTO;
import com.example.ecommerce_web.model.entities.Information;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InformationMapperImpl implements InformationMapper{


    ModelMapper modelMapper;

    @Autowired
    public InformationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public InformationRespondDTO toDTO(Information entity) {
        return modelMapper.map(entity, InformationRespondDTO.class);
    }

    @Override
    public Information toExistedInformation(ModifyUserRequestDTO requestDTO, Information information) {
        String address = requestDTO.getAddress();
        Location location = Location.getLocation(address);
        modelMapper.map(requestDTO, information);
        information.setAddress(location);
        return information;
    }

    @Override
    public Information fromUserRequest(UserRequestDTO userRequestDTO) {
        String address = userRequestDTO.getAddress();
        Location location = Location.getLocation(address);
        Information information = modelMapper.map(userRequestDTO, Information.class);
        information.setAddress(location);
        return information;
    }
}

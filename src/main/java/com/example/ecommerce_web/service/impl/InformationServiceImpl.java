package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.model.Location;
import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.InformationRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.InformationService;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class InformationServiceImpl implements InformationService {

    InformationRepository informationRepository;
    UserRepository userRepository;
    MyDateUtil myDateUtil;
    ModelMapper modelMapper;
    UserLocal userLocal;

    @Autowired
    public InformationServiceImpl(InformationRepository informationRepository, UserRepository userRepository,
                                  MyDateUtil myDateUtil, ModelMapper modelMapper, UserLocal userLocal) {
        this.informationRepository = informationRepository;
        this.userRepository = userRepository;
        this.myDateUtil = myDateUtil;
        this.modelMapper = modelMapper;
        this.userLocal = userLocal;
    }

    @Override
    public ResponseEntity<?> modifyInformation(ModifyUserRequestDTO modifyUserRequestDTO) {

        String userName = userLocal.getLocalUserName();
        Users users = this.userRepository.findUserByUserName(userName).get();
        Information information = users.getInformation();
        String address = modifyUserRequestDTO.getAddress();
        Location location = Location.getLocation(address);

        modelMapper.map(modifyUserRequestDTO, information);
        information.setUpdateDate(new Date());
        information.setAddress(location);

        this.informationRepository.save(information);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Update Account Successfully !!!!"));
    }

    @Override
    public InformationRespondDTO getDetailInformation() {
        String userName = userLocal.getLocalUserName();
        Users users = this.userRepository.findUserByUserName(userName).get();

        int userId = users.getUserId();

        Information information = this.informationRepository.getInformationByUsers(userId);

        return modelMapper.map(information, InformationRespondDTO.class);
    }

    @Override
    public Information createInformationByExistedUser(UserRequestDTO userRequestDTO, Users users) {
        Information information = modelMapper.map(userRequestDTO, Information.class);
        information.setCreateDate(new Date());
        information.setUsers(users);

        String location = userRequestDTO.getAddress();
        Location address = Location.getLocation(location);
        information.setAddress(address);

        return information;
    }
}

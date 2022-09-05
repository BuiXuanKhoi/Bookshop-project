package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.service.InformationService;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public InformationServiceImpl(InformationRepository informationRepository, UserRepository userRepository,
                                  MyDateUtil myDateUtil, ModelMapper modelMapper) {
        this.informationRepository = informationRepository;
        this.userRepository = userRepository;
        this.myDateUtil = myDateUtil;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> modifyInformation(ModifyUserRequestDTO modifyRequestDTO) {

        String userName = modifyRequestDTO.getUserName();

        Information information = modelMapper.map(modifyRequestDTO,Information.class);

        Optional<Users> usersOptional = this.userRepository.findUserByUserName(userName);

        Users users = usersOptional.get();

        Date createDate = users.getInformation().getCreateDate();

        int informationGetID = users.getInformation().getInformationId();

        Date updateDay = new Date();

        information.setCreateDate(createDate);

        information.setInformationId(informationGetID);

        information.setUpdateDate(updateDay);

        information.setUsers(users);

        this.informationRepository.save(information);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Update Account Successfully !!!!"));
    }
}

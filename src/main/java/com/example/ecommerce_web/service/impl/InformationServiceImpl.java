package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.InformationMapper;
import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.InformationService;
import com.example.ecommerce_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InformationServiceImpl implements InformationService {

    private final InformationRepository informationRepository;
    private final UserLocal userLocal;
    private final InformationMapper informationMapper;
    private final UserService userService;

    @Autowired
    public InformationServiceImpl(InformationRepository informationRepository
                                    , UserLocal userLocal, InformationMapper informationMapper,
                                  UserService userService) {
        this.informationRepository = informationRepository;
        this.userService = userService;
        this.userLocal = userLocal;
        this.informationMapper = informationMapper;
    }

    @Override
    public Information update(ModifyUserRequestDTO modifyUserRequestDTO) {
        String userName = userLocal.getLocalUserName();
        Users users = this.userService.findByUserName(userName);
        Information information = informationMapper.toExistedInformation(modifyUserRequestDTO, users.getInformation());
        return this.informationRepository.save(information);
    }
    @Override
    public Information createInformationByExistedUser(UserRequestDTO userRequestDTO, Users users) {
        Information information = informationMapper.fromUserRequest(userRequestDTO);
        information.setUsers(users);
        return information;
    }

    @Override
    public Information getById(int id) {
        return this.informationRepository.findById(id)
                                         .orElseThrow(
                                                 () -> new ResourceNotFoundException("Information not exists with ID: " + id));
    }

    @Override
    public Information getByLocalUser() {
        String userName = userLocal.getLocalUserName();
        Users users = userService.findByUserName(userName);
        return users.getInformation();
    }
}

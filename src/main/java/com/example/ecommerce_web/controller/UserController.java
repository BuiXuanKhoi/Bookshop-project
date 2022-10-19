package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.mapper.InformationMapper;
import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.InformationRespondDTO;
import com.example.ecommerce_web.model.dto.respond.UserRespondDTO;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.service.InformationService;
import com.example.ecommerce_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(maxAge = 3600, origins = "*")
public class UserController {

    UserService userService;
    InformationService informationService;
    InformationMapper informationMapper;

    @Autowired
    public UserController(UserService userService,   InformationService informationService, InformationMapper informationMapper) {
        this.userService = userService;
        this.informationService = informationService;
        this.informationMapper = informationMapper;
    }

    @PutMapping ("/information" )
    public InformationRespondDTO modifyInformation(@RequestBody @Valid ModifyUserRequestDTO modifyUserRequestDTO){
        Information information =  this.informationService.update(modifyUserRequestDTO);
        return informationMapper.toDTO(information);
    }

    @PutMapping( "/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequestDTO changePasswordRequestDTO){
        return this.userService.changePassword(changePasswordRequestDTO);
    }

    @GetMapping
    public Page<UserRespondDTO> getUserList(
            @RequestParam(name = "page",required = false,defaultValue = "0") String page){
        int pageConvert = Integer.parseInt(page);
        return this.userService.getPage(pageConvert);
    }

    @GetMapping("/information")
    public InformationRespondDTO getInformationDetail(){
        Information information =  this.informationService.getByLocalUser();
        return informationMapper.toDTO(information);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> blockUser(@PathVariable("id") int id){
        return this.userService.block(id);
    }

    @PutMapping(value = "/{id}/state")
    public ResponseEntity<?>  unblockUser(@PathVariable("id") int id) {return this.userService.unblock(id);}
}

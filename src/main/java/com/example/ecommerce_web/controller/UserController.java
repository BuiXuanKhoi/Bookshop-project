package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.InformationRespondDTO;
import com.example.ecommerce_web.service.InformationService;
import com.example.ecommerce_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;
    InformationService informationService;


    @Autowired
    public UserController(UserService userService,   InformationService informationService) {
        this.userService = userService;
        this.informationService = informationService;
    }

    @PutMapping ("/information" )
    public ResponseEntity<?> modifyInformation(@RequestBody  ModifyUserRequestDTO modifyRequestDTO){
        return this.informationService.modifyInformation(modifyRequestDTO);
    }

    @PutMapping(value = "/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        return this.userService.changePassword(changePasswordRequestDTO);
    }

    @GetMapping
    public InformationRespondDTO getInformationDetail(){
        return this.informationService.getDetailInformation();
    }
}

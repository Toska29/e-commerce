package com.phoenix.web.controllers;

import com.phoenix.data.dtos.AppUserRequestDto;
import com.phoenix.data.dtos.AppUserResponseDto;
import com.phoenix.services.user.AppUserService;
import com.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody AppUserRequestDto requestDto){
        try{
            AppUserResponseDto response = appUserService.createUser(requestDto);
            return ResponseEntity.ok().body(response);
        } catch (BusinessLogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

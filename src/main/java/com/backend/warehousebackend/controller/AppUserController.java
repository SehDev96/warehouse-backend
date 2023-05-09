package com.backend.warehousebackend.controller;

import com.backend.warehousebackend.entity.AppUser;
import com.backend.warehousebackend.model.ErrorResponseModel;
import com.backend.warehousebackend.model.ResponseModel;
import com.backend.warehousebackend.service.AppUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/app/users")
@RestController
public class AppUserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AppUserService appUserService;

    @PostMapping("/add")
    public ResponseEntity<?> addUserAdminRole(@RequestBody AppUser appUser){
        if(appUser.getPassword().isBlank() || appUser.getUsername().isBlank() || appUser.getRole().isBlank()){
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad request",
                    "Incomplete details"
            ),HttpStatus.BAD_REQUEST);
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser = appUserService.insertUser(appUser);
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully created user",
                appUser
        ),HttpStatus.OK);
    }

    @GetMapping("/admin/list")
    public ResponseEntity<?> getUserListAdminRole(){
        List<AppUser> appUserList = appUserService.getAllUsersAdminRole();
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully retrieved users",
                appUserList
        ),HttpStatus.OK);
    }

    @GetMapping("/manager/list")
    public ResponseEntity<?> getUserListManagerRole(){
        List<AppUser> appUserList = appUserService.getAllUsersManagerRole();
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully retrieved users",
                appUserList
        ),HttpStatus.OK);
    }

}

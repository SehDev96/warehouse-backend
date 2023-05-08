package com.backend.warehousebackend.controller;

import com.backend.warehousebackend.entity.AppUser;
import com.backend.warehousebackend.model.ResponseModel;
import com.backend.warehousebackend.service.AppUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/app/users")
@RestController
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    @PostMapping("/admin")
    public ResponseEntity<?> addUserAdminRole(@RequestBody AppUser appUser){
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

}

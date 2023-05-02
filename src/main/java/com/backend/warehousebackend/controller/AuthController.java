package com.backend.warehousebackend.controller;

import com.backend.warehousebackend.model.BaseResponseModel;
import com.backend.warehousebackend.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/app")
@RestController
public class AuthController {

    @PostMapping("/admin/login")
    public ResponseEntity<BaseResponseModel> adminLogin(@RequestBody LoginRequest loginRequest){
        return null;
    }

}

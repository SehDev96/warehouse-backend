package com.backend.warehousebackend.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.warehousebackend.model.BaseResponseModel;
import com.backend.warehousebackend.model.LoginRequest;
import com.backend.warehousebackend.model.ResponseModel;
import com.backend.warehousebackend.service.UserDetailsServiceImpl;
import com.backend.warehousebackend.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/app/auth")
@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/admin/login")
    public ResponseEntity<BaseResponseModel> adminLogin(@RequestBody LoginRequest loginRequest){
        return null;
    }


    @GetMapping("/refresh-token")
    public void renewAccessToken(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Authentication authentication)
            throws IOException {
        JwtUtils jwtUtils = new JwtUtils();
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = jwtUtils.decodeJwt(refresh_token);
                String username = decodedJWT.getSubject();


                User user = (User) userDetailsService.loadUserByUsername(username);

                String access_token = jwtUtils.generateAccessToken(user);

//                Map<String, String> tokens = new HashMap<>();
//                tokens.put("access_token", access_token);
//                tokens.put("refresh_token",refresh_token);
                response.setContentType("application/json");
                response.setStatus(HttpStatus.OK.value());
                new ObjectMapper().writeValue(response.getOutputStream(),new ResponseModel(
                        HttpStatus.OK.value(),
                        "Token has been renewed",
                        access_token
                ));
            }catch (TokenExpiredException jwtException){
                System.out.println("Error loggin in: "+jwtException.getMessage());
                response.setHeader("error",jwtException.getMessage());
                response.setStatus(403);
//                    response.sendError(FORBIDDEN.value());

                Map<String,String> error = new HashMap<>();
                error.put("error_message",jwtException.getMessage());
                error.put("reason","token_expired");
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
            catch (Exception exception){
                System.out.println("Error loggin in: "+exception.getMessage());
                response.setHeader("error",exception.getMessage());
                response.setStatus(403);
//                    response.sendError(FORBIDDEN.value());

                Map<String,String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        }
    }

}

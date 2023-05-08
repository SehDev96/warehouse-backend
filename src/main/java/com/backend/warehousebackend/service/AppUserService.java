package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser getUserByUsername(String username);

    AppUser insertUser(AppUser appUser);

    List<AppUser> getAllUsersAdminRole();

    List<AppUser> getAllUsersManagerRole();

}

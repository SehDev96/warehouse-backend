package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppUser;
import com.backend.warehousebackend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public AppUser getUserByUsername(String username) {
        return appUserRepository.findUserByUsername(username);
    }

    @Override
    public AppUser insertUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public List<AppUser> getAllUsersAdminRole() {
        return appUserRepository.findAllOrderByRole();
    }

    @Override
    public List<AppUser> getAllUsersManagerRole() {
        return appUserRepository.findAllOrderByRoleManager();
    }
}

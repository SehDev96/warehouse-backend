package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser,UUID> {

    AppUser findUserByUsername(String username);

}

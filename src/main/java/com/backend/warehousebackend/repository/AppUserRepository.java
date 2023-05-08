package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser,UUID> {

    AppUser findUserByUsername(String username);

    @Query(value = "select * from app_user order by role",nativeQuery = true)
    List<AppUser> findAllOrderByRole();

}

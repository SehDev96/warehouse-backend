package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppDestination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppDestinationRepository extends JpaRepository<AppDestination, UUID> {

    Optional<AppDestination> findByName(String name);
}

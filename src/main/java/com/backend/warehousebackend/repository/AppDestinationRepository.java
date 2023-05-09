package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppDestinationRepository extends JpaRepository<AppDestination, UUID> {

    Optional<AppDestination> findByName(String name);

    @Query("SELECT e.id FROM AppDestination e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UUID> searchDestinationIdByName(@Param("keyword") String keyword);

    @Query("select p.name from AppDestination p where p.id = :id")
    Optional<String> findNameById(UUID id);
}

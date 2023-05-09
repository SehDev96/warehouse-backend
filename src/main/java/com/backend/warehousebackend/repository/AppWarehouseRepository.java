package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppInboundTransaction;
import com.backend.warehousebackend.entity.AppWarehouse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppWarehouseRepository extends JpaRepository<AppWarehouse, UUID> {

    Optional<AppWarehouse> findByCode(String code);

    @Query(value = "select * from app_warehouse order by date_created",nativeQuery = true)
    List<AppWarehouse> findAllOrderByDateCreated();

    @Query(value = "select code from app_warehouse order by code",nativeQuery = true)
    List<String> findAllCodes();

    @Query("SELECT e.id FROM AppWarehouse e WHERE LOWER(e.code) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UUID> searchWarehouseIdByCode(@Param("keyword") String keyword);

    @Query("select p.code from AppWarehouse p where p.id = :id")
    Optional<String> findWarehouseCodeById(UUID id);
}

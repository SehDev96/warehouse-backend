package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppWarehouse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppWarehouseRepository extends JpaRepository<AppWarehouse, UUID> {

    Optional<AppWarehouse> findByCode(String code);

    @Query(value = "select * from app_warehouse order by date_created",nativeQuery = true)
    List<AppWarehouse> findAllOrderByDateCreated();
}

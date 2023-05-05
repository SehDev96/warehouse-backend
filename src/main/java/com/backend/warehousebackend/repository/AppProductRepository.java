package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppProductRepository extends JpaRepository<AppProduct, UUID> {

    Optional<AppProduct> findAppProductByName(String name);

    @Query(value = "select count(*) from app_product", nativeQuery = true)
    Optional<Integer> getNumberOfProducts();

    @Query(value = "select * from app_product where name ilike %:key%", nativeQuery = true)
    List<AppProduct> searchProducts(@Param("key") String key);
}

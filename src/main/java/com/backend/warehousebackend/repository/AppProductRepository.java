package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppOutboundTransaction;
import com.backend.warehousebackend.entity.AppProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppProductRepository extends JpaRepository<AppProduct, UUID> {

    Optional<AppProduct> findAppProductByName(String name);

    Optional<AppProduct> findAppProductBySku(String sku);

    @Query(value = "select count(*) from app_product", nativeQuery = true)
    Optional<Integer> getNumberOfProducts();

    @Query(value = "select count(*) from app_product where name ilike %:key%",nativeQuery = true)
    Optional<Integer> searchProductsCount(@Param("key") String key);

    @Query(value = "select * from app_product where name ilike %:key%", nativeQuery = true)
    List<AppProduct> searchProducts(@Param("key") String key);

    Page<AppProduct> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT p FROM AppProduct p WHERE lower(p.sku) LIKE %:keyword% OR lower(p.name) LIKE %:keyword% OR lower(p.description) LIKE %:keyword%")
    Page<AppProduct> findAllContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);


    Optional<AppProduct> findBySku(String sku);

    @Query("select p.sku from AppProduct p where p.id = :id")
    Optional<String> findSkuById(UUID id);

    @Query("SELECT e.id FROM AppProduct e WHERE LOWER(e.sku) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UUID> searchProductIdBySku(@Param("keyword") String keyword);

}

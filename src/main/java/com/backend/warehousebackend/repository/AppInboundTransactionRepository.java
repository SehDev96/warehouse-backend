package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppInboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.UUID;

public interface AppInboundTransactionRepository extends JpaRepository<AppInboundTransaction, UUID> {

    @Query("SELECT e FROM AppInboundTransaction e WHERE LOWER(e.reference) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AppInboundTransaction> searchAppInboundTransactionByReference(@Param("keyword") String keyword);

    @Query("SELECT t FROM AppInboundTransaction t WHERE t.warehouseId IN :warehouse_id")
    List<AppInboundTransaction> getAppInboundTransactionByWarehouseId(@Param("warehouse_id") List<UUID> warehouseIdList);

    @Query("SELECT t FROM AppInboundTransaction t WHERE t.productId IN :product_id")
    List<AppInboundTransaction> getAppInboundTransactionByProductId(@Param("product_id") List<UUID> productIdList);

    @Query("SELECT e FROM AppInboundTransaction e WHERE LOWER(e.remarks) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AppInboundTransaction> searchAppInboundTransactionByRemarks(@Param("keyword") String keyword);
}

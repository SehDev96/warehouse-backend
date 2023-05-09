package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppInboundTransaction;
import com.backend.warehousebackend.entity.AppOutboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AppOutboundTransactionRepository extends JpaRepository<AppOutboundTransaction, UUID> {

    @Query("SELECT e FROM AppOutboundTransaction e WHERE LOWER(e.reference) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AppOutboundTransaction> searchAppOutboundTransactionByReference(@Param("keyword") String keyword);

    @Query("SELECT t FROM AppOutboundTransaction t WHERE t.productId IN :product_id")
    List<AppOutboundTransaction> getAppOutboundTransactionByProductId(@Param("product_id") List<UUID> productIdList);

    @Query("SELECT e FROM AppOutboundTransaction e WHERE LOWER(e.remarks) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AppOutboundTransaction> searchAppOutboundTransactionByRemarks(@Param("keyword") String keyword);

    @Query("SELECT t FROM AppOutboundTransaction t WHERE t.destinationId IN :destination_id")
    List<AppOutboundTransaction> getAppOutboundTransactionByDestinationId(@Param("destination_id") List<UUID> destinationIdList);

}

package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppOutboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppOutboundTransactionRepository extends JpaRepository<AppOutboundTransaction, UUID> {
}

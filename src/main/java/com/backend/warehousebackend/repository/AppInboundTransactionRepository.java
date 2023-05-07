package com.backend.warehousebackend.repository;

import com.backend.warehousebackend.entity.AppInboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppInboundTransactionRepository extends JpaRepository<AppInboundTransaction, UUID> {
}

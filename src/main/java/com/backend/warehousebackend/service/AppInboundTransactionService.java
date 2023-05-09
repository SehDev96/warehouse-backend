package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppInboundTransaction;

import java.util.List;
import java.util.UUID;

public interface AppInboundTransactionService {

    AppInboundTransaction insertInboundTransaction(AppInboundTransaction appInboundTransaction);

    List<AppInboundTransaction> getInboundTransactionListByLocation(List<UUID> warehouseId);

    List<AppInboundTransaction> getInboundTransactionListByReference(String keyword);

    List<AppInboundTransaction> getInboundTransactionListByProductId(List<UUID> productIdList);

    List<AppInboundTransaction> getInboundTransactionListByRemarks(String keyword);


}

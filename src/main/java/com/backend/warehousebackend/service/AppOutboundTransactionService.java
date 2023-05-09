package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppOutboundTransaction;

import java.util.List;
import java.util.UUID;

public interface AppOutboundTransactionService {
    AppOutboundTransaction insertOutboundTransaction(AppOutboundTransaction appInboundTransaction);

    List<AppOutboundTransaction> getOutboundTransactionListByReference(String reference);

    List<AppOutboundTransaction> getOutboundTransactionListByProductId(List<UUID> productIdList);

    List<AppOutboundTransaction> getOutboundTransactionListByRemarks(String keyword);

    List<AppOutboundTransaction> getOutboundTransactionListByDestination(List<UUID> destinationId);
}

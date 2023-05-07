package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppOutboundTransaction;

public interface AppOutboundTransactionService {
    AppOutboundTransaction insertOutboundTransaction(AppOutboundTransaction appInboundTransaction);
}

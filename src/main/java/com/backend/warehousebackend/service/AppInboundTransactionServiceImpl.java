package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppInboundTransaction;
import com.backend.warehousebackend.repository.AppInboundTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppInboundTransactionServiceImpl implements AppInboundTransactionService{

    @Autowired
    private AppInboundTransactionRepository appInboundTransactionRepository;

    @Override
    public AppInboundTransaction insertInboundTransaction(AppInboundTransaction appInboundTransaction) {
        return appInboundTransactionRepository.save(appInboundTransaction);
    }
}

package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppOutboundTransaction;
import com.backend.warehousebackend.repository.AppOutboundTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppOutboundTransactionServiceImpl implements AppOutboundTransactionService{

    @Autowired
    private AppOutboundTransactionRepository appOutboundTransactionRepository;

    @Override
    public AppOutboundTransaction insertOutboundTransaction(AppOutboundTransaction appOutboundTransaction) {
        return appOutboundTransactionRepository.save(appOutboundTransaction);
    }
}

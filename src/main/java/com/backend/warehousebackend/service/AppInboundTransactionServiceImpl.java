package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppInboundTransaction;
import com.backend.warehousebackend.repository.AppInboundTransactionRepository;
import com.backend.warehousebackend.repository.AppProductRepository;
import com.backend.warehousebackend.repository.AppWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppInboundTransactionServiceImpl implements AppInboundTransactionService{

    @Autowired
    private AppInboundTransactionRepository appInboundTransactionRepository;

    @Autowired
    private AppWarehouseRepository appWarehouseRepository;

    @Autowired
    private AppProductRepository appProductRepository;

    @Override
    public AppInboundTransaction insertInboundTransaction(AppInboundTransaction appInboundTransaction) {
        return appInboundTransactionRepository.save(appInboundTransaction);
    }

    @Override
    public List<AppInboundTransaction> getInboundTransactionListByLocation(List<UUID> warehouseId) {
        List<AppInboundTransaction> appInboundTransactionList = appInboundTransactionRepository.getAppInboundTransactionByWarehouseId(warehouseId);
        if(appInboundTransactionList.size()>0){
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                appWarehouseRepository.findWarehouseCodeById(appInboundTransaction.getWarehouseId()).ifPresent(appInboundTransaction::setWarehouseCode);
            }
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                appProductRepository.findSkuById(appInboundTransaction.getProductId()).ifPresent(appInboundTransaction::setProductSku);
            }
        }
        return appInboundTransactionRepository.getAppInboundTransactionByWarehouseId(warehouseId);
    }

    @Override
    public List<AppInboundTransaction> getInboundTransactionListByReference(String keyword) {
        List<AppInboundTransaction> appInboundTransactionList = appInboundTransactionRepository.searchAppInboundTransactionByReference(keyword);
        if(appInboundTransactionList.size()>0){
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                appProductRepository.findSkuById(appInboundTransaction.getProductId()).ifPresent(appInboundTransaction::setProductSku);
            }
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                appWarehouseRepository.findWarehouseCodeById(appInboundTransaction.getWarehouseId()).ifPresent(appInboundTransaction::setWarehouseCode);
            }
        }
        return appInboundTransactionList;
    }

    @Override
    public List<AppInboundTransaction> getInboundTransactionListByProductId(List<UUID> productIdList) {
        List<AppInboundTransaction> appInboundTransactionList = appInboundTransactionRepository.getAppInboundTransactionByProductId(productIdList);
        if(appInboundTransactionList.size()>0){
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                appProductRepository.findSkuById(appInboundTransaction.getProductId()).ifPresent(appInboundTransaction::setProductSku);
            }
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                appWarehouseRepository.findWarehouseCodeById(appInboundTransaction.getWarehouseId()).ifPresent(appInboundTransaction::setWarehouseCode);
            }
        }
        return appInboundTransactionList;
    }

    @Override
    public List<AppInboundTransaction> getInboundTransactionListByRemarks(String keyword) {
        List<AppInboundTransaction> appInboundTransactionList =  appInboundTransactionRepository.searchAppInboundTransactionByRemarks(keyword);
        if(appInboundTransactionList.size()>0){
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                appProductRepository.findSkuById(appInboundTransaction.getProductId()).ifPresent(appInboundTransaction::setProductSku);
            }
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                appWarehouseRepository.findWarehouseCodeById(appInboundTransaction.getWarehouseId()).ifPresent(appInboundTransaction::setWarehouseCode);
            }
        }
        return appInboundTransactionList;
    }
}

package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppInboundTransaction;
import com.backend.warehousebackend.entity.AppOutboundTransaction;
import com.backend.warehousebackend.repository.AppDestinationRepository;
import com.backend.warehousebackend.repository.AppOutboundTransactionRepository;
import com.backend.warehousebackend.repository.AppProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppOutboundTransactionServiceImpl implements AppOutboundTransactionService{

    @Autowired
    private AppOutboundTransactionRepository appOutboundTransactionRepository;

    @Autowired
    private AppProductRepository appProductRepository;

    @Autowired
    private AppDestinationRepository appDestinationRepository;

    @Override
    public AppOutboundTransaction insertOutboundTransaction(AppOutboundTransaction appOutboundTransaction) {
        return appOutboundTransactionRepository.save(appOutboundTransaction);
    }

    @Override
    public List<AppOutboundTransaction> getOutboundTransactionListByReference(String reference) {
        List<AppOutboundTransaction> appOutboundTransactionList = appOutboundTransactionRepository.searchAppOutboundTransactionByReference(reference);
        if(appOutboundTransactionList.size()>0){
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactionList){
                appProductRepository.findSkuById(appOutboundTransaction.getProductId()).ifPresent(appOutboundTransaction::setProductSku);
            }
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactionList){
                appDestinationRepository.findNameById(appOutboundTransaction.getDestinationId()).ifPresent(appOutboundTransaction::setDestination);
            }
        }
        return appOutboundTransactionList;
    }

    @Override
    public List<AppOutboundTransaction> getOutboundTransactionListByProductId(List<UUID> productIdList) {
        List<AppOutboundTransaction> appOutboundTransactionList = appOutboundTransactionRepository.getAppOutboundTransactionByProductId(productIdList);
        if(appOutboundTransactionList.size()>0){
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactionList){
                appProductRepository.findSkuById(appOutboundTransaction.getProductId()).ifPresent(appOutboundTransaction::setProductSku);
            }
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactionList){
                appDestinationRepository.findNameById(appOutboundTransaction.getDestinationId()).ifPresent(appOutboundTransaction::setDestination);
            }
        }
        return appOutboundTransactionList;
    }

    @Override
    public List<AppOutboundTransaction> getOutboundTransactionListByRemarks(String keyword) {
        List<AppOutboundTransaction> appOutboundTransactionList = appOutboundTransactionRepository.searchAppOutboundTransactionByRemarks(keyword);
        if(appOutboundTransactionList.size()>0){
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactionList){
                appProductRepository.findSkuById(appOutboundTransaction.getProductId()).ifPresent(appOutboundTransaction::setProductSku);
            }
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactionList){
                appDestinationRepository.findNameById(appOutboundTransaction.getDestinationId()).ifPresent(appOutboundTransaction::setDestination);
            }
        }
        return appOutboundTransactionList;
    }

    @Override
    public List<AppOutboundTransaction> getOutboundTransactionListByDestination(List<UUID> destinationId) {

        List<AppOutboundTransaction> appOutboundTransactions = appOutboundTransactionRepository.getAppOutboundTransactionByDestinationId(destinationId);

        if(appOutboundTransactions.size()>0){
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactions){
                appDestinationRepository.findNameById(appOutboundTransaction.getDestinationId()).ifPresent(appOutboundTransaction::setDestination);
            }
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactions){
                appProductRepository.findSkuById(appOutboundTransaction.getProductId()).ifPresent(appOutboundTransaction::setProductSku);
            }
        }

        return appOutboundTransactionRepository.getAppOutboundTransactionByDestinationId(destinationId);
    }
}

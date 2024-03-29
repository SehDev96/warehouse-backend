package com.backend.warehousebackend.controller;

import com.backend.warehousebackend.entity.*;
import com.backend.warehousebackend.model.ErrorResponseModel;
import com.backend.warehousebackend.model.ResponseModel;
import com.backend.warehousebackend.model.TransactionWrapper;
import com.backend.warehousebackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RequestMapping("/app/transaction")
@RestController
public class AppTransactionController {

    @Autowired
    private AppInboundTransactionService appInboundTransactionService;

    @Autowired
    private AppOutboundTransactionService appOutboundTransactionService;

    @Autowired
    private AppDestinationService appDestinationService;

    @Autowired
    private AppProductService appProductService;

    @Autowired
    private AppWarehouseService appWarehouseService;

    @PostMapping("/inbound/add")
    public ResponseEntity<?> addInboundTransaction(@RequestBody AppInboundTransaction appInboundTransaction) {
        // check criteria --> product id and warehouse id

        AppProduct appProduct = appProductService.getProductBySku(appInboundTransaction.getProductSku());
        if (appProduct != null) {
            appInboundTransaction.setProductId(appProduct.getId());
            appProduct.setQuantity(appProduct.getQuantity() + appInboundTransaction.getQuantity());
        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Product sku not found"
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        AppWarehouse appWarehouse = appWarehouseService.getWarehouseByCode(appInboundTransaction.getWarehouseCode());

        if (appWarehouse != null) {
            appInboundTransaction.setWarehouseId(appWarehouse.getId());
        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Warehouse code not found"
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        appProductService.addProduct(appProduct);
        appInboundTransaction.setDateCreated(new Timestamp(System.currentTimeMillis()));
        appInboundTransaction = appInboundTransactionService.insertInboundTransaction(appInboundTransaction);


        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully added transaction",
                appInboundTransaction
        ), HttpStatus.OK);
    }

    @PostMapping("/inbound/add/upload")
    public ResponseEntity<?> addInboundTransactionFromCsv(@RequestParam("file") MultipartFile file) throws IOException {
        List<AppInboundTransaction> appInboundTransactionList = new ArrayList<>();
        //TODO some transaction may have proper data --> process that
        //TODO the unsuccessful transaction return back to frontend
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            if (fileName.endsWith(".csv")) {
                byte[] bytes = file.getBytes();
                String content = new String(bytes);

                String[] dataArray = content.split("\r\n");
                for (int i = 1; i < dataArray.length; i++) {
                    AppInboundTransaction appInboundTransaction = new AppInboundTransaction(dataArray[i]);

                    AppProduct appProduct = appProductService.getProductBySku(appInboundTransaction.getProductSku());
                    if (appProduct != null) {
                        appInboundTransaction.setProductId(appProduct.getId());
                        appProduct.setQuantity(appProduct.getQuantity() + appInboundTransaction.getQuantity());
                    } else {
                        return new ResponseEntity<>(new ErrorResponseModel(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "Product sku not found"
                        ), HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    AppWarehouse appWarehouse = appWarehouseService.getWarehouseByCode(appInboundTransaction.getWarehouseCode());

                    if (appWarehouse != null) {
                        appInboundTransaction.setWarehouseId(appWarehouse.getId());
                    } else {
                        return new ResponseEntity<>(new ErrorResponseModel(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "Warehouse code not found"
                        ), HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    appProductService.addProduct(appProduct);
                    appInboundTransaction = appInboundTransactionService.insertInboundTransaction(appInboundTransaction);
                    appInboundTransactionList.add(appInboundTransaction);
                }

            } else {
                // File format not supported. Please upload a csv file
                return new ResponseEntity<>(new ErrorResponseModel(
                        HttpStatus.BAD_REQUEST.value(),
                        "File format not supported. Please upload a csv file.",
                        "Please upload a csv file"
                ), HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.BAD_REQUEST.value(),
                    "File is empty. Please upload a CSV file.",
                    "File is empty"
            ), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully added transaction",
                appInboundTransactionList
        ), HttpStatus.OK);
    }

    @PostMapping("/outbound/add")
    public ResponseEntity<?> addOutboundTransaction(@RequestBody AppOutboundTransaction appOutboundTransaction) {
        // check criteria --> product id
        //TODO some transaction may have proper data --> process that
        //TODO the unsuccessful transaction return back to frontend
        AppProduct appProduct = appProductService.getProductBySku(appOutboundTransaction.getProductSku());
        if (appProduct != null) {
            appOutboundTransaction.setProductId(appProduct.getId());
            appProduct.setQuantity(appProduct.getQuantity() - appOutboundTransaction.getQuantity());
        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Product sku not found"
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        AppDestination appDestination = appDestinationService.getDestinationByName(appOutboundTransaction.getDestination());

        if (appDestination != null) {
            appOutboundTransaction.setDestinationId(appDestination.getId());
        } else {
            appDestination = new AppDestination();
            appDestination.setName(appOutboundTransaction.getDestination());
            appDestination = appDestinationService.insertDestination(appDestination);
            appOutboundTransaction.setDestinationId(appDestination.getId());
        }

        appProductService.addProduct(appProduct);
        appOutboundTransaction.setDateCreated(new Timestamp(System.currentTimeMillis()));
        appOutboundTransaction = appOutboundTransactionService.insertOutboundTransaction(appOutboundTransaction);


        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully added transaction",
                appOutboundTransaction
        ), HttpStatus.OK);
    }

    @PostMapping("/outbound/add/upload")
    public ResponseEntity<?> addOutboundTransactionFromCsv(@RequestParam("file") MultipartFile file) throws IOException {
        List<AppOutboundTransaction> appOutboundTransactionList = new ArrayList<>();
        //TODO some transaction may have proper data --> process that
        //TODO the unsuccessful transaction return back to frontend
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            if (fileName.endsWith(".csv")) {
                byte[] bytes = file.getBytes();
                String content = new String(bytes);

                String[] dataArray = content.split("\r\n");
                for (int i = 1; i < dataArray.length; i++) {
                    AppOutboundTransaction appOutboundTransaction = new AppOutboundTransaction(dataArray[i]);

                    AppProduct appProduct = appProductService.getProductBySku(appOutboundTransaction.getProductSku());
                    if (appProduct != null) {
                        appOutboundTransaction.setProductId(appProduct.getId());
                        appProduct.setQuantity(appProduct.getQuantity() - appOutboundTransaction.getQuantity());
                    } else {
                        return new ResponseEntity<>(new ErrorResponseModel(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "Product sku not found"
                        ), HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    AppDestination appDestination = appDestinationService.getDestinationByName(appOutboundTransaction.getDestination());

                    if (appDestination != null) {
                        appOutboundTransaction.setDestinationId(appDestination.getId());
                    } else {
                        appDestination = new AppDestination();
                        appDestination.setName(appOutboundTransaction.getDestination());
                        appDestination = appDestinationService.insertDestination(appDestination);
                        appOutboundTransaction.setDestinationId(appDestination.getId());
                    }

                    appProductService.addProduct(appProduct);
                    appOutboundTransaction = appOutboundTransactionService.insertOutboundTransaction(appOutboundTransaction);
                    appOutboundTransactionList.add(appOutboundTransaction);
                }

            } else {
                // File format not supported. Please upload a csv file
                return new ResponseEntity<>(new ErrorResponseModel(
                        HttpStatus.BAD_REQUEST.value(),
                        "File format not supported. Please upload a csv file.",
                        "Please upload a csv file"
                ), HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.BAD_REQUEST.value(),
                    "File is empty. Please upload a CSV file.",
                    "File is empty"
            ), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully added transaction",
                appOutboundTransactionList
        ), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTransactionList(@RequestParam(name = "option") String option,
                                                @RequestParam(name = "keyword") String keyword
    ) {
        List<AppInboundTransaction> appInboundTransactionList = new ArrayList<>();
        List<AppOutboundTransaction> appOutboundTransactionsList = new ArrayList<>();

        switch (option) {
            case "LOCATION": {
                List<UUID> warehouseIdList = appWarehouseService.getWarehouseIdListByCode(keyword);
                if(warehouseIdList.size()>0){
                    appInboundTransactionList = appInboundTransactionService.getInboundTransactionListByLocation(warehouseIdList);
                }

            }
            break;
            case "DESTINATION": {
                List<UUID> destinationIdList = appDestinationService.getDestinationIdListByName(keyword);
                if(destinationIdList.size()>0){
                    appOutboundTransactionsList = appOutboundTransactionService.getOutboundTransactionListByDestination(destinationIdList);
                }
            }
            break;
            case "REFERENCE": {
                appInboundTransactionList = appInboundTransactionService.getInboundTransactionListByReference(keyword);
                appOutboundTransactionsList = appOutboundTransactionService.getOutboundTransactionListByReference(keyword);
            }
            break;
            case "PRODUCT_SKU": {
                List<UUID> productIdList = appProductService.getProductIdListBySku(keyword);
                if(productIdList.size()>0){
                    appInboundTransactionList = appInboundTransactionService.getInboundTransactionListByProductId(productIdList);
                    appOutboundTransactionsList = appOutboundTransactionService.getOutboundTransactionListByProductId(productIdList);
                }

            }
            break;
            case "REMARKS": {
                appInboundTransactionList = appInboundTransactionService.getInboundTransactionListByRemarks(keyword);
                appOutboundTransactionsList = appOutboundTransactionService.getOutboundTransactionListByRemarks(keyword);
            }
            break;
            default:
                return new ResponseEntity<>(new ErrorResponseModel(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Option not recognized"
                ), HttpStatus.BAD_REQUEST);

        }

        List<TransactionWrapper> transactionWrappers = new ArrayList<>();

        if(appOutboundTransactionsList.size()>0){
            for(AppOutboundTransaction appOutboundTransaction: appOutboundTransactionsList){
                transactionWrappers.add(new TransactionWrapper(appOutboundTransaction.getDateCreated(),appOutboundTransaction));
            }
        }

        if(appInboundTransactionList.size()>0){
            for(AppInboundTransaction appInboundTransaction: appInboundTransactionList){
                transactionWrappers.add(new TransactionWrapper(appInboundTransaction.getDateCreated(),appInboundTransaction));
            }
        }

        transactionWrappers.sort(Comparator.comparing(TransactionWrapper::getCreatedDate));


        List<Object> transactionResultList = new ArrayList<>();

        for(TransactionWrapper transactionWrapper: transactionWrappers){
            transactionResultList.add(transactionWrapper.getTransaction());
        }




        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully retrieved transaction list",
                transactionResultList
        ),HttpStatus.OK);
    }
}

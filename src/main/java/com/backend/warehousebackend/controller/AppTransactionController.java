package com.backend.warehousebackend.controller;

import com.backend.warehousebackend.entity.AppInboundTransaction;
import com.backend.warehousebackend.entity.AppProduct;
import com.backend.warehousebackend.entity.AppWarehouse;
import com.backend.warehousebackend.model.ErrorResponseModel;
import com.backend.warehousebackend.model.ResponseModel;
import com.backend.warehousebackend.service.AppInboundTransactionService;
import com.backend.warehousebackend.service.AppProductService;
import com.backend.warehousebackend.service.AppWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/app/transaction")
@RestController
public class AppTransactionController {

    @Autowired
    private AppInboundTransactionService appInboundTransactionService;

    @Autowired
    private AppProductService appProductService;

    @Autowired
    private AppWarehouseService appWarehouseService;

    @PostMapping("/inbound/add")
    public ResponseEntity<?> addInboundTransaction(@RequestBody AppInboundTransaction appInboundTransaction){
        // check criteria --> product id and warehouse id

        AppProduct appProduct = appProductService.getProductBySku(appInboundTransaction.getProductSku());
        if(appProduct != null){
            appInboundTransaction.setProductId(appProduct.getId());
            appProduct.setQuantity(appProduct.getQuantity()+appInboundTransaction.getQuantity());
        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Product sku not found"
            ),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        AppWarehouse appWarehouse = appWarehouseService.getWarehouseByCode(appInboundTransaction.getWarehouseCode());

        if(appWarehouse != null){
            appInboundTransaction.setWarehouseId(appWarehouse.getId());
        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Warehouse code not found"
            ),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        appProductService.addProduct(appProduct);
        appInboundTransaction.setDateCreated(new Timestamp(System.currentTimeMillis()));
        appInboundTransaction = appInboundTransactionService.insertInboundTransaction(appInboundTransaction);


        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully added transaction",
                appInboundTransaction
        ),HttpStatus.OK);
    }

    @PostMapping("/inbound/add/upload")
    public ResponseEntity<?> addInboundTransactionFromCsv(@RequestParam("file")MultipartFile file) throws IOException {
        List<AppInboundTransaction> appInboundTransactionList = new ArrayList<>();
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            if (fileName.endsWith(".csv")) {
                byte[] bytes = file.getBytes();
                String content = new String(bytes);

                String[] dataArray = content.split("\r\n");
                for(int i=1;i<dataArray.length;i++){
                    AppInboundTransaction appInboundTransaction = new AppInboundTransaction(dataArray[i]);

                    AppProduct appProduct = appProductService.getProductBySku(appInboundTransaction.getProductSku());
                    if(appProduct != null){
                        appInboundTransaction.setProductId(appProduct.getId());
                        appProduct.setQuantity(appProduct.getQuantity()+appInboundTransaction.getQuantity());
                    } else {
                        return new ResponseEntity<>(new ErrorResponseModel(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "Product sku not found"
                        ),HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    AppWarehouse appWarehouse = appWarehouseService.getWarehouseByCode(appInboundTransaction.getWarehouseCode());

                    if(appWarehouse != null){
                        appInboundTransaction.setWarehouseId(appWarehouse.getId());
                    } else {
                        return new ResponseEntity<>(new ErrorResponseModel(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "Warehouse code not found"
                        ),HttpStatus.INTERNAL_SERVER_ERROR);
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

        }  else {
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
        ),HttpStatus.OK);
    }
}

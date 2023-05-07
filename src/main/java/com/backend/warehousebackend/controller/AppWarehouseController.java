package com.backend.warehousebackend.controller;

import com.backend.warehousebackend.entity.AppWarehouse;
import com.backend.warehousebackend.model.ErrorResponseModel;
import com.backend.warehousebackend.model.ResponseModel;
import com.backend.warehousebackend.service.AppWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequestMapping("/app/warehouse")
@RestController
public class AppWarehouseController {

    @Autowired
    private AppWarehouseService appWarehouseService;

    @PostMapping("/add")
    public ResponseEntity<?> addWarehouse(@RequestBody AppWarehouse appWarehouse) {
        appWarehouse.setCode(appWarehouse.getCode().toUpperCase());

        if (appWarehouseService.getWarehouseByCode(appWarehouse.getCode()) != null) {
            return new ResponseEntity<>(new ResponseModel(
                    HttpStatus.CONFLICT.value(),
                    "Warehouse code already exists",
                    appWarehouse.getCode()
            ), HttpStatus.CONFLICT);
        }

        appWarehouse = appWarehouseService.addWarehouse(appWarehouse);

        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully added warehouse",
                appWarehouse
        ), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllWarehouse() {
        List<AppWarehouse> appWarehouses = appWarehouseService.getAllWarehouseOrderByDateCreated();
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully retrieved all warehouse details",
                appWarehouses
        ), HttpStatus.OK);

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateWarehouse(@RequestBody AppWarehouse appWarehouse) {
        AppWarehouse dbWarehouse = appWarehouseService.getWarehouseById(appWarehouse.getId());
        if (dbWarehouse != null) {
            appWarehouse.setDateCreated(dbWarehouse.getDateCreated());
            appWarehouse = appWarehouseService.updateWarehouse(appWarehouse);
            return new ResponseEntity<>(new ResponseModel(
                    HttpStatus.OK.value(),
                    "Successfully updated warehouse details",
                    appWarehouse
            ), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Warehouse details not found"
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

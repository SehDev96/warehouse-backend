package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppWarehouse;
import com.backend.warehousebackend.repository.AppWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppWarehouseServiceImpl implements AppWarehouseService{

    @Autowired
    private AppWarehouseRepository appWarehouseRepository;


    @Override
    public AppWarehouse getWarehouseById(UUID id) {
        return appWarehouseRepository.findById(id).orElse(null);
    }

    @Override
    public AppWarehouse addWarehouse(AppWarehouse appWarehouse) {
        return appWarehouseRepository.save(appWarehouse);
    }

    @Override
    public AppWarehouse updateWarehouse(AppWarehouse appWarehouse) {
        return appWarehouseRepository.save(appWarehouse);
    }

    @Override
    public AppWarehouse getWarehouseByCode(String code) {
        return appWarehouseRepository.findByCode(code).orElse(null);
    }

    @Override
    public List<AppWarehouse> getAllWarehouse() {
        return appWarehouseRepository.findAll();
    }

    @Override
    public List<AppWarehouse> getAllWarehouseOrderByDateCreated() {
        return appWarehouseRepository.findAllOrderByDateCreated();
    }

    @Override
    public List<String> getAllWarehouseCodes() {
        return appWarehouseRepository.findAllCodes();
    }
}

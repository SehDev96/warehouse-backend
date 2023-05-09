package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppWarehouse;

import java.util.List;
import java.util.UUID;

public interface AppWarehouseService {

    AppWarehouse getWarehouseById(UUID id);

    AppWarehouse addWarehouse(AppWarehouse appWarehouse);

    AppWarehouse updateWarehouse(AppWarehouse appWarehouse);

    AppWarehouse getWarehouseByCode(String code);

    List<AppWarehouse> getAllWarehouse();

    List<AppWarehouse> getAllWarehouseOrderByDateCreated();

    List<String> getAllWarehouseCodes();

    List<UUID> getWarehouseIdListByCode(String keyword);

}

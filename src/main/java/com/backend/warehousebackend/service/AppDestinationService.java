package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppDestination;

public interface AppDestinationService {

    AppDestination getDestinationByName(String name);

    AppDestination insertDestination(AppDestination appDestination);
}

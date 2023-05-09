package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppDestination;

import java.util.List;
import java.util.UUID;

public interface AppDestinationService {

    AppDestination getDestinationByName(String name);

    AppDestination insertDestination(AppDestination appDestination);

    List<UUID> getDestinationIdListByName(String keyword);
}

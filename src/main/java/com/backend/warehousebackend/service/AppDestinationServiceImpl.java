package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppDestination;
import com.backend.warehousebackend.repository.AppDestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppDestinationServiceImpl implements AppDestinationService{

    @Autowired
    private AppDestinationRepository appDestinationRepository;

    @Override
    public AppDestination getDestinationByName(String name) {
        return appDestinationRepository.findByName(name).orElse(null);
    }

    @Override
    public AppDestination insertDestination(AppDestination appDestination) {
        return appDestinationRepository.save(appDestination);
    }
}

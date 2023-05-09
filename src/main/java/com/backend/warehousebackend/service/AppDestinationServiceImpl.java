package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppDestination;
import com.backend.warehousebackend.repository.AppDestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<UUID> getDestinationIdListByName(String keyword) {
        return appDestinationRepository.searchDestinationIdByName(keyword);
    }
}

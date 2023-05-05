package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppProduct;
import com.backend.warehousebackend.repository.AppProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppProductServiceImpl implements AppProductService{

    @Autowired
    private AppProductRepository appProductRepository;

    @Override
    public List<AppProduct> getAllProducts() {
        return appProductRepository.findAll();
    }

    @Override
    public AppProduct getProductByid(UUID id) {
        return appProductRepository.findById(id).orElse(null);
    }

    @Override
    public AppProduct getProductByName(String name) {
        return appProductRepository.findAppProductByName(name).orElse(null);
    }

    @Override
    public int getNumberOfProducts() {
        return appProductRepository.getNumberOfProducts().orElse(null);
    }

    @Override
    public AppProduct addProduct(AppProduct appProduct) {
        return appProductRepository.save(appProduct);
    }

    @Override
    public AppProduct updateProduct(AppProduct appProduct) {
        return appProductRepository.save(appProduct);
    }

    @Override
    public List<AppProduct> searchProduct(String key) {
        return appProductRepository.searchProducts(key);
    }
}

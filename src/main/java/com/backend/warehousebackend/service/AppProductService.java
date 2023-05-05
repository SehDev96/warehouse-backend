package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppProduct;

import java.util.List;
import java.util.UUID;

public interface AppProductService {

    List<AppProduct> getAllProducts();

    AppProduct getProductByid(UUID id);

    AppProduct getProductByName(String name);

    AppProduct addProduct(AppProduct appProduct);

    AppProduct updateProduct(AppProduct appProduct);

    List<AppProduct> searchProduct(String key);

    int getNumberOfProducts();
}

package com.backend.warehousebackend.service;

import com.backend.warehousebackend.entity.AppProduct;
import com.backend.warehousebackend.repository.AppProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<AppProduct> getAllProductsByPagination(String q,
                                                       int pageIndex,
                                                       int pageSize,
                                                       String sortBy,
                                                       String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        Page<AppProduct> appProductPage = null;

        if (q == null) {
            appProductPage = appProductRepository.findAll(pageable);
        } else {
            appProductPage = appProductRepository.findAllContainingIgnoreCase(q, pageable);
        }

        assert appProductPage != null;
        return appProductPage.getContent();
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
    public AppProduct getProductBySku(String sku) {
        return appProductRepository.findAppProductBySku(sku).orElse(null);
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

    @Override
    public int searchProductCount(String key) {
        return appProductRepository.searchProductsCount(key).orElse(null);
    }

    @Override
    public List<UUID> getProductIdListBySku(String keyword) {
        return appProductRepository.searchProductIdBySku(keyword);
    }
}

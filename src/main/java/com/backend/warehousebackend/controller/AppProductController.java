package com.backend.warehousebackend.controller;

import com.backend.warehousebackend.entity.AppProduct;
import com.backend.warehousebackend.model.ErrorResponseModel;
import com.backend.warehousebackend.model.ResponseModel;
import com.backend.warehousebackend.service.AppProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequestMapping("/app/product")
@RestController
public class AppProductController {

    @Autowired
    private AppProductService appProductService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody AppProduct appProduct) {
        appProduct.setName(appProduct.getName().toUpperCase());

        if (appProductService.getProductByName(appProduct.getName()) != null) {
            return new ResponseEntity<>(new ResponseModel(
                    HttpStatus.CONFLICT.value(),
                    "Product already exists",
                    appProduct
            ), HttpStatus.CONFLICT);
        }

        int productCount = appProductService.getNumberOfProducts();
        appProduct.setSku(productCount + 1);

        appProduct = appProductService.addProduct(appProduct);


        System.out.println("Testing");
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully added product",
                appProduct
        ), HttpStatus.OK);
    }

    @PostMapping("/add/upload")
    public ResponseEntity<?> addProductsFromCsv(@RequestParam("file")
                                                        MultipartFile file) throws IOException {
        List<AppProduct> uploadedProductsList = new ArrayList<>();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            if (fileName.endsWith(".csv")) {
                // process the CSV file
                byte[] bytes = file.getBytes();
                String content = new String(bytes);

                String[] dataArray = content.split("\r\n");
                for (int i = 1; i < dataArray.length; i++) {
                    AppProduct appProduct = new AppProduct(dataArray[i]);
                    int productCount = appProductService.getNumberOfProducts();
                    appProduct.setSku(productCount + 1);
                    appProduct.setName(appProduct.getName().toUpperCase());
                    appProduct = appProductService.addProduct(appProduct);
                    uploadedProductsList.add(appProduct);
                }
                return new ResponseEntity<>(new ResponseModel(
                        HttpStatus.OK.value(),
                        "Successfully uploaded products",
                        uploadedProductsList
                ), HttpStatus.OK);
            } else {
                // File format not supported. Please upload a csv file
                return new ResponseEntity<>(new ErrorResponseModel(
                        HttpStatus.BAD_REQUEST.value(),
                        "File format not supported. Please upload a csv file.",
                        "Please upload a csv file"
                ), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ErrorResponseModel(
                    HttpStatus.BAD_REQUEST.value(),
                    "File is empty. Please upload a CSV file.",
                    "File is empty"
            ), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allproducts")
    public ResponseEntity<?> getAllProducts() {
        List<AppProduct> appProductList = new ArrayList<>(appProductService.getAllProducts());
        //appProductList.sort(Comparator.comparing(AppProduct::getSku));

        appProductList.sort(new Comparator<AppProduct>() {
            @Override
            public int compare(AppProduct o1, AppProduct o2) {
                String[] o1String = o1.getSku().split("PRDSKU");
                String[] o2String = o2.getSku().split("PRDSKU");

                int num1 = Integer.parseInt(o1String[1]);
                int num2 = Integer.parseInt(o2String[1]);
                return Integer.compare(num1, num2);
            }
        });

        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully retrieved all products",
                appProductList
        ), HttpStatus.OK);
    }

    @GetMapping("/all/pagination")
    public ResponseEntity<?> getAllProjects(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "pageIndex",defaultValue = "0",required = false) int pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC", required = false) String sortDirection
    ) {
        List<AppProduct> appProductList = new ArrayList<>(appProductService.getAllProductsByPagination(q, pageIndex, pageSize, sortBy, sortDirection));

        if (sortBy.equalsIgnoreCase("sku")) {
            appProductList.sort(new Comparator<AppProduct>() {
                @Override
                public int compare(AppProduct o1, AppProduct o2) {
                    String[] o1String = o1.getSku().split("PRDSKU");
                    String[] o2String = o2.getSku().split("PRDSKU");

                    int num1 = Integer.parseInt(o1String[1]);
                    int num2 = Integer.parseInt(o2String[1]);
                    return Integer.compare(num1, num2);
                }
            });
        }

        Map<String, Object> response = new HashMap<>();

        int totalSize = 0;
        if(q == null || q.isEmpty() || q.isBlank()){
            totalSize = appProductService.getNumberOfProducts();
        } else {
            response.put("query",q);
            totalSize = appProductService.searchProductCount(q);

        }

        response.put("total_size", totalSize);
        response.put("data", appProductList);


        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Successfully retrieved all the projects",
                response
        ), HttpStatus.OK);
    }


    @PutMapping("/updateproduct/{product_id}")
    public ResponseEntity<?> editProduct(@PathVariable String product_id, @RequestBody Map<String, String> productField) {
        AppProduct appProduct = appProductService.getProductByid(UUID.fromString(product_id));
        appProduct.setPrice(Double.parseDouble(productField.get("price")));
        appProduct.setQuantity(Integer.parseInt(productField.get("quantity")));
        appProduct.setName(productField.get("name"));
        appProduct.setDescription(productField.get("description"));
        appProduct = appProductService.updateProduct(appProduct);
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Product successfully updated",
                appProduct
        ), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam(required = false) String key) {
        List<AppProduct> appProductList = appProductService.searchProduct(key);
        return new ResponseEntity<>(new ResponseModel(
                HttpStatus.OK.value(),
                "Product list successfully retrieved",
                appProductList
        ), HttpStatus.OK);
    }

}

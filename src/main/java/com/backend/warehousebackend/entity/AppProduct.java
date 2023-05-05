package com.backend.warehousebackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Table(name = "app_product")
public class AppProduct {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @Column
    private String sku;

    @Column
    private String description;

    @Column
    private int quantity;

    @Column
    private double price;

    public void setSku(int i) {
        this.sku = "PRDSKU"+i;
    }

    public AppProduct() {
    }

    public AppProduct(String csvData) {
        if(csvData.contains(",")){
            String[] dataArray = csvData.split(",");
            if(dataArray.length>0 && dataArray.length<5){
                this.setName(dataArray[0]);
                this.setDescription(dataArray[1]);
                this.setQuantity(Integer.parseInt(dataArray[2]));
                this.setPrice(Double.parseDouble(dataArray[3]));
            } else {
                // throw exception
            }
        }

    }
}

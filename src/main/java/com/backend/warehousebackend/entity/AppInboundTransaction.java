package com.backend.warehousebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "app_inbound_transaction")
public class AppInboundTransaction {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Transient
    private String productSku;

    @Column
    private String reference;

    @Column
    private int quantity;

    @Column(name = "date_received")
    private String dateReceived;

    @Column(name = "warehouse_id")
    private UUID warehouseId;

    @Transient
    private String warehouseCode;

    @Column
    private String remarks;

    @Column(name = "date_created")
    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

    public AppInboundTransaction() {
    }

    public AppInboundTransaction(String csvData) {
        if(csvData.contains(",")){
            String[] dataArray = csvData.split(",");
            if(dataArray.length>0 && dataArray.length<8){
                this.setReference(dataArray[1]);
                this.setDateReceived(dataArray[2]);
                this.setProductSku(dataArray[3]);
                this.setQuantity(Integer.parseInt(dataArray[4]));
                this.setWarehouseCode(dataArray[5]);
                this.setRemarks(dataArray[6]);
            } else {
                // throw exception
            }
        }
    }
}

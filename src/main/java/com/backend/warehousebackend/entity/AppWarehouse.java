package com.backend.warehousebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "app_warehouse")
public class AppWarehouse {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String address;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String description;

    @Column(name = "date_created")
    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

}

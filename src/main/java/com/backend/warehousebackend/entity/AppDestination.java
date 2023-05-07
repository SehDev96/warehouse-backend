package com.backend.warehousebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "app_destination")
public class AppDestination {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @Column(name = "date_created")
    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

}

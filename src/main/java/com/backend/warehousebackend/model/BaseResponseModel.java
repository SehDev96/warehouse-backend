package com.backend.warehousebackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class BaseResponseModel {
    private boolean success;
    private int response_code;
    private String message;
    private Date timestamp = new Date();
}

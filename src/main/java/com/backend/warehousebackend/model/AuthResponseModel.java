package com.backend.warehousebackend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthResponseModel extends BaseResponseModel{
    private Map<String,String> payload;
}

package com.example.shop.api;

import com.fasterxml.jackson.databind.JsonSerializable;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(description = "API Errors", value = "API Errors")
public class Errors implements Serializable {

    public static final String PRODUCT_NOT_FOUND = "404";
    public static final String PRODUCT_NOT_FOUND_MSG = "Product not found.";

    public static final String ERROR_RETRIEVING_PRODUCT = "500";
    public static final String ERROR_RETRIEVING_PRODUCT_MSG = "Error at retrieving the product.";

}
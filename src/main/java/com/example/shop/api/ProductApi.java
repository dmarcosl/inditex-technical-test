package com.example.shop.api;

import com.example.shop.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductApi {

    @Operation(summary = "Similar products", description = "", tags = {})
    @ApiResponses({
            @ApiResponse(responseCode = Errors.PRODUCT_NOT_FOUND, description = Errors.PRODUCT_NOT_FOUND_MSG),
            @ApiResponse(responseCode = Errors.ERROR_RETRIEVING_PRODUCT, description = Errors.ERROR_RETRIEVING_PRODUCT_MSG),
            @ApiResponse(responseCode = "200", description = "Product recovered successfully.")
    })
    @GetMapping(value = "/product/{productId}/similar")
    ResponseEntity<List<Product>> getProductSimilar(@PathVariable(value = "productId") Integer productId);
}


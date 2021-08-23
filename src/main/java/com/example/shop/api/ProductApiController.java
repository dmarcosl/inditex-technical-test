package com.example.shop.api;

import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import com.example.shop.service.ServiceError;
import com.example.shop.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class ProductApiController implements ProductApi {

    private final ProductService productService;

    @Autowired
    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    public ResponseEntity<List<Product>> getProductSimilar(Integer productId) {

        List<Product> productList = null;
        try {
            productList = productService.getProductSimilar(productId);
        } catch (ServiceException e) {
            if (Objects.equals(e.getServiceError().getCode(), ServiceError.PRODUCT_NOT_FOUND.getCode())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (Objects.equals(e.getServiceError().getCode(), ServiceError.ERROR_RETRIEVING_PRODUCT.getCode())
                    || Objects.equals(e.getServiceError().getCode(), ServiceError.ERROR_PARSING_PRODUCT.getCode())) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}

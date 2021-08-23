package com.example.shop.service;

import com.example.shop.model.Product;

import java.util.List;

public interface ProductService {

    /**
     * Obtain a list of similar products
     *
     * @param productId If of the product
     * @return List of Product entities
     * @throws ServiceException Request error or product not found
     */
    List<Product> getProductSimilar(Integer productId) throws ServiceException;
}

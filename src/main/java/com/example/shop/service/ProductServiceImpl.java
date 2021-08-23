package com.example.shop.service;

import com.example.shop.model.Product;
import com.example.shop.util.HttpClientUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    public final Environment environment;

    @Autowired
    public ProductServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    @Cacheable("products")
    public List<Product> getProductSimilar(Integer productId) throws ServiceException {

        List<Product> productList = new ArrayList<>();

        HttpClient httpClient = HttpClientUtil.httpClient;

        // Obtain the ids of the similar products
        List<Integer> productIdList = requestSimilar(httpClient, productId);

        // Iterate each id to obtain its detail
        for (Integer id : productIdList) {
            Product product = requestDetail(httpClient, id);
            // Add only those products that exist and didn't trigger an error at retrieving them
            if (product != null) {
                productList.add(product);
            }
        }

        return productList;
    }

    /**
     * Make a request to obtain the similar products ids
     *
     * @param httpClient Client http
     * @param productId Id of the product
     * @return List of product ids
     * @throws ServiceException Request error or product not found
     */
    private List<Integer> requestSimilar(HttpClient httpClient, Integer productId) throws ServiceException {

        String uri = String.format("http://localhost:3001/product/%s/similarids", productId);

        HttpRequest httpRequest = HttpClientUtil.getRequest(uri);

        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ServiceException(ServiceError.ERROR_RETRIEVING_PRODUCT);
        }

        if (httpResponse.statusCode() == 500) {
            throw new ServiceException(ServiceError.ERROR_RETRIEVING_PRODUCT);
        } else if (httpResponse.statusCode() == 404) {
            throw new ServiceException(ServiceError.PRODUCT_NOT_FOUND);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(httpResponse.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new ServiceException(ServiceError.ERROR_PARSING_PRODUCT);
        }
    }

    /**
     * Make a request to obtain the detail of a product
     *
     * @param httpClient Client http
     * @param productId Id of the product
     * @return Product entity
     */
    private Product requestDetail(HttpClient httpClient, Integer productId) {

        String uri = String.format("http://localhost:3001/product/%s", productId);

        HttpRequest httpRequest = HttpClientUtil.getRequest(uri);

        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            return null;
        }

        if (httpResponse.statusCode() != 200) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(httpResponse.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

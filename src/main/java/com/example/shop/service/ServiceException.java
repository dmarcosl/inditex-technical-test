package com.example.shop.service;

public class ServiceException extends Exception {

    private final ServiceError serviceError;

    public ServiceException(ServiceError serviceError) {
        super();
        this.serviceError = serviceError;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }
}

package org.example.productfeatureservice.client;

import org.example.productfeatureservice.config.ExternalServiceConfig;
import org.example.productfeatureservice.dto.SupplierResponse;
import org.example.productfeatureservice.exception.ExternalServiceException;
import org.example.productfeatureservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Component
public class SupplierServiceClient {
    private final RestTemplate restTemplate;
    private final String supplierServiceUrl;

    @Autowired
    public SupplierServiceClient(RestTemplate restTemplate, ExternalServiceConfig config) {
        this.restTemplate = restTemplate;
        this.supplierServiceUrl = config.getSupplierServiceUrl();
    }

    public void validateSupplierExists(Long id) {
        try {
            restTemplate.getForEntity(supplierServiceUrl + "/suppliers/" + id, SupplierResponse.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Supplier with ID " + id + " not found");
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to validate supplier: " + ex.getMessage());
        }
    }

    public SupplierResponse getSupplierById(Long id) {
        try {
            ResponseEntity<SupplierResponse> response = restTemplate.getForEntity(
                    supplierServiceUrl + "/suppliers/" + id, SupplierResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Supplier with ID " + id + " not found");
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to fetch supplier: " + ex.getMessage());
        }
    }

    public void assignProductToSupplier(Long supplierId, Long productId) {
        try {
            restTemplate.put(
                    supplierServiceUrl + "/suppliers/" + supplierId + "/products/" + productId,
                    null);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Supplier with ID " + supplierId + " not found");
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to assign product to supplier: " + ex.getMessage());
        }
    }

    public void removeProductFromSupplier(Long supplierId, Long productId) {
        try {
            restTemplate.delete(
                    supplierServiceUrl + "/suppliers/" + supplierId + "/products/" + productId);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Supplier with ID " + supplierId + " not found");
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to remove product from supplier: " + ex.getMessage());
        }
    }
}
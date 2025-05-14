package org.example.productfeatureservice.client;

import org.example.productfeatureservice.config.ExternalServiceConfig;
import org.example.productfeatureservice.dto.PaginatedResponse;
import org.example.productfeatureservice.dto.ProductResponse;
import org.example.productfeatureservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class ProductCatalogClient {
    private final RestTemplate restTemplate;
    private final String productCatalogApiUrl;

    @Autowired
    public ProductCatalogClient(RestTemplate restTemplate, ExternalServiceConfig config) {
        this.restTemplate = restTemplate;
        this.productCatalogApiUrl = config.getProductCatalogApiUrl();
    }

    public void validateProductExists(Long id) {
        try {
            restTemplate.getForEntity(productCatalogApiUrl + "/products/" + id, ProductResponse.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found");
        }
    }

    public ProductResponse getProductById(Long id) {
        try {
            ResponseEntity<ProductResponse> response = restTemplate.getForEntity(
                    productCatalogApiUrl + "/products/" + id, ProductResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found");
        }
    }

    public PaginatedResponse<ProductResponse> getProductsPaginated(int page, int size) {
        ResponseEntity<PaginatedResponse<ProductResponse>> response = restTemplate.exchange(
                productCatalogApiUrl + "/products?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginatedResponse<ProductResponse>>() {});
        return Objects.requireNonNull(response.getBody());
    }
}
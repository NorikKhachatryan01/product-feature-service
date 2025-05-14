package org.example.productfeatureservice.service;

import org.example.productfeatureservice.client.ProductCatalogClient;
import org.example.productfeatureservice.client.SupplierServiceClient;
import org.example.productfeatureservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductFeatureService {
    private final ProductCatalogClient productCatalogClient;
    private final SupplierServiceClient supplierServiceClient;

    @Autowired
    public ProductFeatureService(ProductCatalogClient productCatalogClient,
                                 SupplierServiceClient supplierServiceClient) {
        this.productCatalogClient = productCatalogClient;
        this.supplierServiceClient = supplierServiceClient;
    }

    public PaginatedResponse<ProductFeatureResponse> getProducts(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be non-negative and size must be positive");
        }
        PaginatedResponse<ProductResponse> productPage = productCatalogClient.getProductsPaginated(page, size);

        List<ProductFeatureResponse> featureResponses = productPage.getContent().stream()
                .map(product -> {
                    ProductFeatureResponse response = new ProductFeatureResponse();
                    response.setProductId(product.getId());
                    response.setName(product.getName());
                    response.setSku(product.getSku());
                    response.setPrice(product.getPrice());
                    response.setCategoryName(product.getCategory());
                    response.setDescription(product.getDescription());
                    Long supplierId = product.getId() % 5 + 1; // Simulated supplier ID
                    try {
                        SupplierResponse supplier = supplierServiceClient.getSupplierById(supplierId);
                        response.setSupplierId(supplier.getId());
                        response.setSupplierName(supplier.getName());
                    } catch (Exception e) {
                        response.setSupplierId(null);
                        response.setSupplierName("Unknown");
                    }
                    return response;
                })
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                featureResponses,
                new PaginatedResponse.PaginationMetadata(
                        productPage.getPagination().getTotalItems(),
                        productPage.getPagination().getCurrentPage(),
                        productPage.getPagination().getPageSize(),
                        productPage.getPagination().getTotalPages()
                )
        );
    }

    public void assignSupplier(Long productId, AssignSupplierRequest request) {
        productCatalogClient.validateProductExists(productId);
        supplierServiceClient.validateSupplierExists(request.getSupplierId());
        supplierServiceClient.assignProductToSupplier(request.getSupplierId(), productId);
    }

    public void removeSupplier(Long productId, Long supplierId) {
        productCatalogClient.validateProductExists(productId);
        supplierServiceClient.validateSupplierExists(supplierId);
        supplierServiceClient.removeProductFromSupplier(supplierId, productId);
    }
}
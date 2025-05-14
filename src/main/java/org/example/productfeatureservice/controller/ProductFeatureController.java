package org.example.productfeatureservice.controller;

import org.example.productfeatureservice.dto.AssignSupplierRequest;
import org.example.productfeatureservice.dto.PaginatedResponse;
import org.example.productfeatureservice.dto.ProductFeatureResponse;
import org.example.productfeatureservice.service.ProductFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/features/products")
public class ProductFeatureController {
    private final ProductFeatureService productFeatureService;

    @Autowired
    public ProductFeatureController(ProductFeatureService productFeatureService) {
        this.productFeatureService = productFeatureService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ProductFeatureResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be non-negative and size must be positive");
        }
        PaginatedResponse<ProductFeatureResponse> response = productFeatureService.getProducts(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/assign-supplier")
    public ResponseEntity<Void> assignSupplier(
            @PathVariable Long id,
            @RequestBody AssignSupplierRequest request) {
        productFeatureService.assignSupplier(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/supplier/{supplierId}")
    public ResponseEntity<Void> removeSupplier(
            @PathVariable Long id,
            @PathVariable Long supplierId) {
        productFeatureService.removeSupplier(id, supplierId);
        return ResponseEntity.noContent().build();
    }
}
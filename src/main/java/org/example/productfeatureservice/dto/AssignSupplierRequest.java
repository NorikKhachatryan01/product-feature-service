package org.example.productfeatureservice.dto;


import lombok.Data;

@Data
public class AssignSupplierRequest {
    private Long supplierId;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
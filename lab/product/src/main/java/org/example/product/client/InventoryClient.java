package org.example.product.client;

import org.example.product.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "inventory_service", url = "http://localhost:8082")
public interface InventoryClient {

    @GetMapping("/api/inventory/{skuCode}")
    InventoryResponse getInventoryBySkuCode(@PathVariable String skuCode);
}
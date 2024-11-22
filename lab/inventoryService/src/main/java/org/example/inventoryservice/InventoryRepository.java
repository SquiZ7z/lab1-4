package org.example.inventoryservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Метод для перевірки наявності товару за skuCode та кількістю
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, int quantity);
    Inventory findBySkuCode(String skuCode);
}
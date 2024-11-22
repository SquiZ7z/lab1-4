package org.example.inventoryservice;

//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode, int quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    @Transactional(readOnly = true)
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Inventory getInventoryBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode);
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

}
package org.example.product;

import org.example.product.client.InventoryClient;
import org.example.product.dto.InventoryResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
public class ProductInventoryServiceTest {
    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private ProductService productService;

    public ProductInventoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductInventory() {
        String skuCode = "12345";
        InventoryResponse mockInventory = new InventoryResponse(skuCode, 100);
        when(inventoryClient.getInventoryBySkuCode(skuCode)).thenReturn(mockInventory);

        InventoryResponse inventory = productService.getProductInventory(skuCode);

        assertNotNull(inventory);
        assertEquals(skuCode, inventory.getSkuCode());
        assertEquals(100, inventory.getQuantity());
    }
}

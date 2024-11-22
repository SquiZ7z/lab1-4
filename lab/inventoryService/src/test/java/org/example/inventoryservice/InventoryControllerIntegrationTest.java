package org.example.inventoryservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework .test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllInventories() throws Exception {
        mockMvc.perform(get("/api/inventory"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInventoryBySkuCode() throws Exception {
        mockMvc.perform(get("/api/inventory/{skuCode}", "example-sku"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveInventory() throws Exception {
        String inventoryJson = "{\"skuCode\":\"example-sku\",\"quantity\":100}";

        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inventoryJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testIsInStock() throws Exception {
        mockMvc.perform(get("/api/inventory/check-stock?skuCode=example-sku&quantity=10"))
                .andExpect(status().isOk());
    }
}

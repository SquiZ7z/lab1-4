package org.example.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        // Очищення бази даних перед кожним тестом
        productRepository.deleteAll();

        // Додавання тестових даних з конкретними ID
        Product product1 = Product.builder()
                .id("1") // Встановіть ID вручну
                .name("Test Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(10.99))
                .build();

        Product product2 = Product.builder()
                .id("2") // Встановіть ID вручну
                .name("Test Product 2")
                .description("Description 2")
                .price(BigDecimal.valueOf(20.99))
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
    }
        @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].price").exists());
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists());
    }

    @Test
    public void testCreateProduct() throws Exception {
        String requestBody = "{\"name\":\"New Product\",\"description\":\"This is a new product\",\"price\":10.99}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is a new product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.99));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        String requestBody = "{\"name\":\"Updated Product\",\"description\":\"This is an updated product\",\"price\":9.99}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is an updated product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(9.99));
    }
    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/{id}", "1"))
                .andExpect(status().isNoContent());
    }



}
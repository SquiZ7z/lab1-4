package org.example.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.example.product.client.InventoryClient;
import org.example.product.dto.InventoryResponse;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient; // Додано InventoryClient

    @Autowired
    public ProductService(ProductRepository productRepository, InventoryClient inventoryClient) {
        this.productRepository = productRepository;
        this.inventoryClient = inventoryClient; // Ініціалізація InventoryClient
    }

    public InventoryResponse getProductInventory(String skuCode) {
        return inventoryClient.getInventoryBySkuCode(skuCode);
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        return mapToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public Optional<ProductResponse> getProductById(String id) {
        return productRepository.findById(id)
                .map(this::mapToProductResponse);
    }

    @Transactional
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productRequest.name());
            product.setDescription(productRequest.description());
            product.setPrice(productRequest.price());
            productRepository.save(product);
            log.info("Product {} is updated", product.getId());
            return mapToProductResponse(product); // Повертаємо оновлений продукт
        } else {
            log.info("Product with id {} not found", id);
            return null; // Повертаємо null, якщо продукт не знайдено
        }
    }

    @Transactional
    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            log.info("Product with id {} is deleted", id);
            return true;
        } else {
            log.info("Product with id {} not found, cannot delete", id);
            return false;
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(), product.getPrice());
    }
}
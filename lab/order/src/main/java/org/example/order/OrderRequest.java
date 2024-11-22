package org.example.order;
import java.math.BigDecimal;
public record OrderRequest(Long id, String skuCode, BigDecimal price, Integer
        quantity) {
}

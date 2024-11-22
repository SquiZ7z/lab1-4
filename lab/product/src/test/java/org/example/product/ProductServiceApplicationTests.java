package org.example.product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
// 1лб
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceApplicationTests {
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");
    @LocalServerPort
    private Integer port;
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

    }
    static {
        mongoDBContainer.start();
    }
    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        RestAssured.given()
                .contentType("application/json")
                .body(productRequest)
                .when()
                .post("/api/products")
                .then()
                .log().all()
                .statusCode(201);
    }
    private ProductRequest getProductRequest() {
        return new ProductRequest("iPhone 13", "iPhone 13", BigDecimal.valueOf(1200));
    }
}

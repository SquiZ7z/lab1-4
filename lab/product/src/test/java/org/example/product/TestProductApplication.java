package org.example.product;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc

public class TestProductApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }
}
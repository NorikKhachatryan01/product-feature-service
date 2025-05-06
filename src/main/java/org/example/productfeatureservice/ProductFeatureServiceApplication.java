package org.example.productfeatureservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ProductFeatureServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductFeatureServiceApplication.class, args);
    }

}

package com.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.store.common.entity", "com.store.admin"})
public class StoreBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreBackEndApplication.class, args);
    }

}

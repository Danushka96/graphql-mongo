package com.example.graphqlmongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author danushka
 * 2021-12-23
 */
@Document
@Data
public class Customer {
    private final Integer id;
    private final String name;

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

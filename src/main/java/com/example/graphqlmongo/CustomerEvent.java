package com.example.graphqlmongo;

/**
 * @author danushka
 * 2021-12-24
 */
public record CustomerEvent(Customer customer, CustomerEventType event) {
}

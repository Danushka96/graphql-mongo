package com.example.graphqlmongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author danushka
 * 2021-12-23
 */
public interface CustomerRepository extends ReactiveMongoRepository<Customer, Integer> {
}

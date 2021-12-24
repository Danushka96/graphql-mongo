package com.example.graphqlmongo;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author danushka
 * 2021-12-23
 *
 */
@Controller
@RequiredArgsConstructor
public class CustomerGraphqlController {
    private final CustomerRepository customerRepository;

    @SchemaMapping(typeName = "Customer")
    Flux<Order> orders(Customer customer) {
        var orders = new ArrayList<Order>();
        for (int orderId = 0; orderId < (Math.random() * 100); orderId++) {
            orders.add(new Order(orderId, customer.getId()));
        }
        return Flux.fromIterable(orders);
    }

    @QueryMapping
    Mono<Customer> customerById(@Argument Integer id) {
        return customerRepository.findById(id);
    }

    @QueryMapping
    Flux<Customer> customers() {
        return this.customerRepository.findAll();
    }

    @MutationMapping
    Mono<Customer> addCustomer(@Argument String name) {
        var customer = new Customer(new Random().nextInt(100) + 10, name);
        return customerRepository.save(customer);
    }

    @SubscriptionMapping
    Flux<CustomerEvent> customerEvents(@Argument Integer customerId) {
        return this.customerRepository.findById(customerId)
                .flatMapMany(customer -> {
                    var stream = Stream.generate(
                            () -> new CustomerEvent(customer, Math.random() > 0.5 ? CustomerEventType.DELETED : CustomerEventType.UPDATED));
                    return Flux.fromStream(stream);
                })
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }
}

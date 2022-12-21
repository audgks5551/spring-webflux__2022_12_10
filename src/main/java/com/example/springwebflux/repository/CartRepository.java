package com.example.springwebflux.repository;

import com.example.springwebflux.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {
}

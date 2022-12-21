package com.example.springwebflux.repository;

import com.example.springwebflux.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {
}

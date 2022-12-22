package com.example.springwebflux.service;

import com.example.springwebflux.domain.Item;
import com.example.springwebflux.repository.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

import static org.springframework.data.mongodb.core.query.Criteria.byExample;

public class InventoryService {
    private final ItemRepository itemRepository;
    private final ReactiveFluentMongoOperations fluentMongoOperations;

    public InventoryService(ItemRepository itemRepository, ReactiveFluentMongoOperations fluentMongoOperations) {
        this.itemRepository = itemRepository;
        this.fluentMongoOperations = fluentMongoOperations;
    }

    public Flux<Item> searchByExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");

        Example<Item> probe = Example.of(item, matcher);

        return itemRepository.findAll(probe);
    }

    Flux<Item> searchByFluentExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");

        return fluentMongoOperations.query(Item.class)
                .matching(Query.query(byExample(Example.of(item, matcher))))
                .all();
    }
}

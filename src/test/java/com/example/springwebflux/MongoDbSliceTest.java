package com.example.springwebflux;

import com.example.springwebflux.domain.Item;
import com.example.springwebflux.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

@DataMongoTest
public class MongoDbSliceTest {

    @Autowired
    ItemRepository repository;

    @Test
    void itemRepositorySavesItems() {
        Item sampleItem = new Item("name", "description", 1.99);

        repository.save(sampleItem)
                .as(StepVerifier::create)
                .expectNextMatches(item -> {
                    Assertions.assertThat(item.getId()).isNotNull();
                    Assertions.assertThat(item.getName()).isEqualTo("name");
                    Assertions.assertThat(item.getDescription()).isEqualTo("description");
                    Assertions.assertThat(item.getPrice()).isEqualTo(1.99);

                    return true;
                })
                .verifyComplete();
    }
}

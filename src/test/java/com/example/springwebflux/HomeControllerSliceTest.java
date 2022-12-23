package com.example.springwebflux;

import com.example.springwebflux.controller.HomeController;
import com.example.springwebflux.domain.Cart;
import com.example.springwebflux.domain.Item;
import com.example.springwebflux.service.InventoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(HomeController.class)
public class HomeControllerSliceTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    InventoryService inventoryService;

    @Test
    void homePage() {
        when(inventoryService.getInventory()).thenReturn(Flux.just(
                new Item("id1", "name1", "desc1", 1.99),
                new Item("id2", "name2", "desc2", 9.99)
        ));

        when(inventoryService.getCart("My Cart")).thenReturn(Mono.just(new Cart("My Cart")));

        client.get().uri("/").exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(result -> {
                    Assertions.assertThat(result.getResponseBody()).contains("action=\"/add/id1\"");
                    Assertions.assertThat(result.getResponseBody()).contains("action=\"/add/id2\"");
                });
    }
}

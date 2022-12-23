package com.example.springwebflux.service;

import com.example.springwebflux.domain.Cart;
import com.example.springwebflux.domain.CartItem;
import com.example.springwebflux.repository.CartRepository;
import com.example.springwebflux.repository.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AltInventoryService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public AltInventoryService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }


    public Mono<Cart> addItemToCart(String cartId, String itemId) {
        Cart cart = this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .block();

        return  cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                .findAny()
                .map(cartItem -> {
                    cartItem.increment();
                    return Mono.just(cart);
                })
                .orElseGet(() -> this.itemRepository.findById(itemId)
                        .map(item -> new CartItem(item))
                        .map(cartItem -> {
                            cart.getCartItems ().add(cartItem);
                            return cart;
                        }))
                .flatMap(this.cartRepository::save);
    }
}

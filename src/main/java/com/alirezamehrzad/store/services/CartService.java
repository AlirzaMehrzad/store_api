package com.alirezamehrzad.store.services;

import com.alirezamehrzad.store.dtos.CartDto;
import com.alirezamehrzad.store.dtos.CartItemDto;
import com.alirezamehrzad.store.entities.Cart;
import com.alirezamehrzad.store.exceptions.CartNotFoundException;
import com.alirezamehrzad.store.exceptions.ProductNotFoundException;
import com.alirezamehrzad.store.mappers.CartMapper;
import com.alirezamehrzad.store.repositories.CartRepository;
import com.alirezamehrzad.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if(product == null){
            throw new ProductNotFoundException();
        }

        // => Update cart
        var cartItem = cart.addItem(product);

        // => Save cart
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        return cartMapper.toDto(cart);
    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
           throw new CartNotFoundException();
        }

        var cartItem = cart.getItem(productId);
        if(cartItem == null){
          throw new ProductNotFoundException();
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void removeItem(UUID cartId, Long productId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        cart.clear();
        cartRepository.save(cart);
    }
}

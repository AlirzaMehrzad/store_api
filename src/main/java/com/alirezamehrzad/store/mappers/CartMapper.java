package com.alirezamehrzad.store.mappers;

import com.alirezamehrzad.store.dtos.CartDto;
import com.alirezamehrzad.store.dtos.CartItemDto;
import com.alirezamehrzad.store.entities.Cart;
import com.alirezamehrzad.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}

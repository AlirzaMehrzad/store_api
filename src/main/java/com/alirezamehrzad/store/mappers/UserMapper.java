package com.alirezamehrzad.store.mappers;

import com.alirezamehrzad.store.dtos.RegisterUserRequest;
import com.alirezamehrzad.store.dtos.UpdateUserRequest;
import com.alirezamehrzad.store.dtos.UserDto;
import com.alirezamehrzad.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    void update(UpdateUserRequest request, @MappingTarget User user);
    User toEntity(RegisterUserRequest request);
    UserDto toDto(User user);
}

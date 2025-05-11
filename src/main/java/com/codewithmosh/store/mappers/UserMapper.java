package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.RegisterUserRequestDTO;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDTO;
import com.codewithmosh.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //@Mapping(target = "createdAt", expression="java(java.time.LocalDateTime.now())")
    UserDTO toDto(User user);
    User toEntity(RegisterUserRequestDTO request);

    void update(UpdateUserRequest request, @MappingTarget User user);
}

package com.codewithmosh.store.dtos;

import lombok.Data;

@Data
public class RegisterUserRequestDTO {

    private String name;
    private String email;
    private String password;
}

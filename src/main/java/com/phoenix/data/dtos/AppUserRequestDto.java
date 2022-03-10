package com.phoenix.data.dtos;

import lombok.Data;

@Data
public class AppUserRequestDto {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;

}

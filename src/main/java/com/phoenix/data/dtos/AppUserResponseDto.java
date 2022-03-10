package com.phoenix.data.dtos;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class AppUserResponseDto {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
}

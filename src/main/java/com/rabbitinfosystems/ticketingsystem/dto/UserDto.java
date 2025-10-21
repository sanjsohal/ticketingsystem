package com.rabbitinfosystems.ticketingsystem.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String name;
    private String role;
    private String avatar;
}

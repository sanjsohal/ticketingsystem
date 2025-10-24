package com.rabbitinfosystems.ticketingsystem.mapper;

import com.rabbitinfosystems.ticketingsystem.dto.UserDto;
import com.rabbitinfosystems.ticketingsystem.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}

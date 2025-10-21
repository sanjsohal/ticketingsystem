package com.rabbitinfosystems.ticketingsystem.service;

import com.rabbitinfosystems.ticketingsystem.dto.RegistrationPayload;
import com.rabbitinfosystems.ticketingsystem.dto.UserDto;
import com.rabbitinfosystems.ticketingsystem.mapper.UserMapper;
import com.rabbitinfosystems.ticketingsystem.model.User;
import com.rabbitinfosystems.ticketingsystem.repository.UserRepository;
import com.rabbitinfosystems.ticketingsystem.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AvatarService avatarService;

    public User saveUser(RegistrationPayload registrationPayload) {
        User user = new User();
        user.setFirebaseUid(registrationPayload.firebaseUid());
        user.setName(registrationPayload.name());
        user.setEmail(registrationPayload.email());
        user.setEmailVerified(false);
        return userRepository.save(user);
    }

    public void updateEmailVerifiedFlag(String firebaseUid, boolean isEmailVerified) {
        userRepository.findByFirebaseUid(firebaseUid)
                .ifPresent(user -> {
                    user.setEmailVerified(isEmailVerified);
                    userRepository.save(user);
                });
    }

    public UserDto findUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        User user = userOptional.get();
        UserDto userDto = userMapper.toDto(user);
        userDto.setAvatar(avatarService.getAvatarUrl(user.getFirebaseUid()));
        return userDto;
    }
}

package com.rabbitinfosystems.ticketingsystem.service;

import com.rabbitinfosystems.ticketingsystem.dto.RegistrationPayload;
import com.rabbitinfosystems.ticketingsystem.model.User;
import com.rabbitinfosystems.ticketingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(RegistrationPayload registrationPayload) {
        User user = new User();
        user.setId(registrationPayload.firebaseUid());
        user.setName(registrationPayload.name());
        user.setEmail(registrationPayload.email());
        user.setAvatar(registrationPayload.avatar());
        user.setEmailVerified(false);
        return userRepository.save(user);
    }
}

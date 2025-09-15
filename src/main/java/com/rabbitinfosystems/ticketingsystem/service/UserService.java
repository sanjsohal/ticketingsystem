package com.rabbitinfosystems.ticketingsystem.service;

import com.rabbitinfosystems.ticketingsystem.dto.RegistrationPayload;
import com.rabbitinfosystems.ticketingsystem.dto.VerifyEmailPayload;
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
        user.setFirebaseUid(registrationPayload.firebaseUid());
        user.setName(registrationPayload.name());
        user.setEmail(registrationPayload.email());
        user.setAvatar(registrationPayload.avatar());
        user.setEmailVerified(false);
        return userRepository.save(user);
    }

    public void verifyEmail(VerifyEmailPayload payload) {
        userRepository.findByFirebaseUid(payload.firebaseUid())
                .ifPresent(user -> {
                    user.setEmailVerified(payload.emailVerified());
                    userRepository.save(user);
                });
    }
}

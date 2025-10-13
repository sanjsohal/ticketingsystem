package com.rabbitinfosystems.ticketingsystem.service;

import com.rabbitinfosystems.ticketingsystem.dto.RegistrationPayload;
import com.rabbitinfosystems.ticketingsystem.model.User;
import com.rabbitinfosystems.ticketingsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
}

package com.rabbitinfosystems.ticketingsystem.repository;

import com.rabbitinfosystems.ticketingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);
    Optional<User> findByFirebaseUid(String firebaseUid);
}

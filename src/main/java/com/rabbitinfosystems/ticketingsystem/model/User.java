package com.rabbitinfosystems.ticketingsystem.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 255)
    private String name;

    @Column(length = 50)
    private String role = "USER";  // default role

    @Column(length = 255)
    private String avatar;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


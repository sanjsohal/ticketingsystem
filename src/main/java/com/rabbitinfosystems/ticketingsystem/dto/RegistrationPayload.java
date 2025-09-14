package com.rabbitinfosystems.ticketingsystem.dto;

import java.util.UUID;

public record RegistrationPayload(UUID firebaseUid, String email,
                                  String name, String avatar) {
}

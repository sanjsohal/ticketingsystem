package com.rabbitinfosystems.ticketingsystem.dto;

import java.util.UUID;

public record RegistrationPayload(String firebaseUid, String email,
                                  String name, String avatar) {
}

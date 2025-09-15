package com.rabbitinfosystems.ticketingsystem.dto;

public record VerifyEmailPayload(String firebaseUid, String email, boolean emailVerified) {
}

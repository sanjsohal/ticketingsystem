package com.rabbitinfosystems.ticketingsystem.dto;



public record RegistrationPayload(String firebaseUid, String email,
                                  String name, String avatar) {
}

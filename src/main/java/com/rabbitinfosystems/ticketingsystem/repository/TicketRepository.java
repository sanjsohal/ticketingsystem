package com.rabbitinfosystems.ticketingsystem.repository;

import com.rabbitinfosystems.ticketingsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

}

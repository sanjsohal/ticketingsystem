package com.rabbitinfosystems.ticketingsystem.service;

import com.rabbitinfosystems.ticketingsystem.model.Ticket;
import com.rabbitinfosystems.ticketingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}

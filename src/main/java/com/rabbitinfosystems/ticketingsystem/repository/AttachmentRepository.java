package com.rabbitinfosystems.ticketingsystem.repository;

import com.rabbitinfosystems.ticketingsystem.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}


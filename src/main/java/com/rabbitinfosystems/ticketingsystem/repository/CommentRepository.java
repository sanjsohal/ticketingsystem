package com.rabbitinfosystems.ticketingsystem.repository;

import com.rabbitinfosystems.ticketingsystem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}

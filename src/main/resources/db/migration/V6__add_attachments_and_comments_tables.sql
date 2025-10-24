-- Create attachments table
CREATE TABLE attachments (
    id UUID PRIMARY KEY,
    filename VARCHAR(255) NOT NULL,
    url VARCHAR(512) NOT NULL,
    ticket_id UUID NOT NULL,
    CONSTRAINT fk_attachment_ticket FOREIGN KEY (ticket_id)
        REFERENCES tickets(id) ON DELETE CASCADE
);

-- Create comments table
CREATE TABLE comments (
    id UUID PRIMARY KEY,
    content TEXT NOT NULL,
    author_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ticket_id UUID NOT NULL,
    CONSTRAINT fk_comment_ticket FOREIGN KEY (ticket_id)
        REFERENCES tickets(id) ON DELETE CASCADE
);


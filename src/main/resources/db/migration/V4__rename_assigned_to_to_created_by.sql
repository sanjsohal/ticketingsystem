
ALTER TABLE tickets DROP CONSTRAINT fk_requester;
ALTER TABLE tickets DROP CONSTRAINT tickets_requester_id_fkey;

ALTER TABLE tickets RENAME COLUMN requester_id TO created_by;



ALTER TABLE tickets ADD CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE tickets ADD CONSTRAINT tickets_created_by_fkey FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL



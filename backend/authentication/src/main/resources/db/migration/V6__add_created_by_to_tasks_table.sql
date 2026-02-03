ALTER TABLE tasks
    ADD COLUMN created_by BIGINT NOT NULL;

ALTER TABLE tasks
    ADD CONSTRAINT fk_tasks_created_by
        FOREIGN KEY (created_by) REFERENCES users(id);

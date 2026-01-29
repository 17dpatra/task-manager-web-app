-- =========================
-- Tasks table (user-owned)
-- =========================
DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,

                       title VARCHAR(255),
                       description TEXT,

                       status VARCHAR(50), -- TODO, IN_PROGRESS, COMPLETED

                       user_id BIGINT NOT NULL,

                       CONSTRAINT fk_tasks_user
                           FOREIGN KEY (user_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
);

CREATE INDEX idx_tasks_user_id ON tasks(user_id);
CREATE INDEX idx_tasks_status ON tasks(status);

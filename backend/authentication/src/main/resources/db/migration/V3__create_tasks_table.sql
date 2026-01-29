-- V3__create_tasks_table.sql
-- Creates tasks table (team-scoped) with assignee + creator and status constraint.

CREATE TABLE tasks (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,

                       team_id BIGINT NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,

                       status VARCHAR(20) NOT NULL DEFAULT 'TODO',

                       assigned_user_id BIGINT NULL,
                       created_by BIGINT NOT NULL,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                           ON UPDATE CURRENT_TIMESTAMP,

                       CONSTRAINT fk_tasks_team
                           FOREIGN KEY (team_id) REFERENCES teams(id)
                               ON DELETE CASCADE,

                       CONSTRAINT fk_tasks_assigned_user
                           FOREIGN KEY (assigned_user_id) REFERENCES users(id)
                               ON DELETE SET NULL,

                       CONSTRAINT fk_tasks_created_by
                           FOREIGN KEY (created_by) REFERENCES users(id),

                       CONSTRAINT chk_tasks_status
                           CHECK (status IN ('TODO', 'IN_PROGRESS', 'COMPLETED'))
);

CREATE INDEX idx_tasks_team_id ON tasks(team_id);
CREATE INDEX idx_tasks_assigned_user_id ON tasks(assigned_user_id);
CREATE INDEX idx_tasks_status ON tasks(status);

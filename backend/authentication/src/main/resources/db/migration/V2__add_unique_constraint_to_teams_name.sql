ALTER TABLE teams
    ADD CONSTRAINT uq_teams_name UNIQUE (name);
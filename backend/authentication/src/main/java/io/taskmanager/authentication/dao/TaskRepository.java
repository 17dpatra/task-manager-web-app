package io.taskmanager.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import io.taskmanager.authentication.domain.task.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
}

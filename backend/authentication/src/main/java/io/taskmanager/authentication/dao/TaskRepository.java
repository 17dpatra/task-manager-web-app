package io.taskmanager.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.taskmanager.authentication.domain.task.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    boolean existsByUserIdAndId(Long userId, Long id);

    List<Task> deleteByUserIdAndId(Long userId, Long id);

    List<Task> findByAssigneeId(Long assigneeId);

    @Query("SELECT t FROM Task t JOIN t.assignedUsers u WHERE t.status = :status AND u.id = :userId")
    List<Task> filterTasks(String status, Long userId);
}

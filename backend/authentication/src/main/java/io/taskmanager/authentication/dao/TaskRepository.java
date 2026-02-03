package io.taskmanager.authentication.dao;

import io.taskmanager.authentication.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // ---------------------------
    // userId == assignedTo.id
    // ---------------------------

    @Query("""
        SELECT t
        FROM Task t
        WHERE t.assignedTo.id = :userId
    """)
    List<Task> findByUserId(Long userId);

    @Query("""
        SELECT COUNT(t) > 0
        FROM Task t
        WHERE t.id = :id
          AND t.assignedTo.id = :userId
    """)
    boolean existsByUserIdAndId(Long userId, Long id);

    @Modifying
    @Query("""
        DELETE FROM Task t
        WHERE t.id = :id
          AND t.assignedTo.id = :userId
    """)
    void deleteByUserIdAndId(Long userId, Long id);

    // ---------------------------
    // assigneeId == assignedTo.id
    // ---------------------------

    @Query("""
        SELECT t
        FROM Task t
        WHERE t.assignedTo.id = :assigneeId
    """)
    List<Task> findByAssigneeId(Long assigneeId);

    // ---------------------------
    // Filters
    // ---------------------------

    @Query("""
        SELECT t
        FROM Task t
        WHERE t.status = :status
          AND t.assignedTo.id = :userId
    """)
    List<Task> filterTasks(String status, Long userId);
}
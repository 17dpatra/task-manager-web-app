package io.taskmanager.authentication.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.taskmanager.authentication.dao.TaskRepository;
import io.taskmanager.authentication.domain.task.Task;
import io.taskmanager.authentication.exception.NotFoundException;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task updateTask(Task task) {
        Task existingTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new NotFoundException("Task id not found: " + task.getId()));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long taskId) {

        if (!taskRepository.existsById(taskId)) {
            throw new NotFoundException("Task id not found: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }
    
}

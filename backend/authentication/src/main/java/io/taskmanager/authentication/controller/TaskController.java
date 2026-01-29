package io.taskmanager.authentication.controller;

import java.util.List;

import io.taskmanager.authentication.dao.TaskRepository;
import io.taskmanager.authentication.domain.task.Task;
import io.taskmanager.authentication.dto.task.TaskResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import io.taskmanager.authentication.exception.NotFoundException;
import io.taskmanager.authentication.service.TaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @GetMapping("/get_tasks")
    public List<TaskResponse> getTasks(@RequestParam Long userId) {
        return taskService.getTasksByUser(userId);
    }

    @PutMapping("/update_task")
    public TaskResponse updateTask(@RequestBody Task task) {
        Task updatTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new NotFoundException("Task id not found: " + task.getId()));
        return taskService.updateTask(updatTask);
    }
    
    @DeleteMapping("/delete_task/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}

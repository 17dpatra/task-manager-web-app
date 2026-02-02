package io.taskmanager.authentication.controller;

import java.util.List;
import java.util.Map;

import io.taskmanager.authentication.dao.TaskRepository;
import io.taskmanager.authentication.domain.task.Task;
import io.taskmanager.authentication.dto.task.TaskRequest;
import io.taskmanager.authentication.dto.task.TaskResponse;
import io.taskmanager.authentication.exception.NotFoundException;
import io.taskmanager.authentication.service.TaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @PostMapping("/create_task")
    public List<TaskResponse> createTask(TaskRequest taskRequest) {
        // Implementation for creating a task can be added here
        return taskService.createTask(taskRequest);
    }

    @GetMapping("/get_tasks")
    public Map<String, List<Task>> getTasks(@RequestParam Long userId) {
        return taskService.getTasksGroupedByStatus(userId);
    }

    @PutMapping("/update_task/{user_id}")
    public TaskResponse updateTask(@PathVariable Long user_id, @RequestBody Task task) {
        Task updatTask = taskRepository.findById(user_id)
                .orElseThrow(() -> new NotFoundException("Task id not found: " + user_id));
        return taskService.updateTask(updatTask);
    }
    
    @DeleteMapping("/delete_task/{user_id}/{taskId}")
    public void deleteTask(@PathVariable Long user_id, @PathVariable Long taskId) {
        taskService.deleteTask(user_id, taskId);
    }

    @GetMapping("/filter_tasks")
    public List<TaskResponse> filterTasks(@RequestParam String status, @RequestParam Long userId) {
        return taskService.filterTasks(status, userId);
    }

    // update task does not need taskid in the path, it is in the body

}

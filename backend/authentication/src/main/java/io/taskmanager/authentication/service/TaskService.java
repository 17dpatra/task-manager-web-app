package io.taskmanager.authentication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.taskmanager.authentication.dao.TaskRepository;
import io.taskmanager.authentication.domain.task.Task;
import io.taskmanager.authentication.dto.task.TaskRequest;
import io.taskmanager.authentication.dto.task.TaskResponse;
import io.taskmanager.authentication.exception.NotFoundException;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setStatus(taskRequest.status());
        task.setPriority(taskRequest.priority());
        task.setDueDate(taskRequest.dueDate());

        Task savedTask = taskRepository.save(task);

        return List.of(new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getPriority(),
                savedTask.getDueDate()));
    }
    
    public Map<String, List<Task>> getTasksGroupedByStatus(Long userId) {

        List<Task> tasks = taskRepository.findByAssigneeId(userId);

        Map<String, List<Task>> groupedTasks = new HashMap<>();

        groupedTasks.put("created", new ArrayList<>());
        groupedTasks.put("in-progress", new ArrayList<>());
        groupedTasks.put("validating", new ArrayList<>());
        groupedTasks.put("completed", new ArrayList<>());
        groupedTasks.put("unknown", tasks);

        for (Task task : tasks) {
            String status = task.getStatus().toLowerCase();

            switch (status) {
                case "created":
                    groupedTasks.get("created").add(task);
                    break;
                case "in-progress":
                    groupedTasks.get("in-progress").add(task);
                    break;
                case "validating":
                    groupedTasks.get("validating").add(task);
                    break;
                case "completed":
                    groupedTasks.get("completed").add(task);
                    break;
                default:
                    // optional: handle unknown status
                    groupedTasks.get("unknown").add(task);
                    break;
            }
        }

        return groupedTasks;
    }

    public List<TaskResponse> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDueDate()
                ))
                .toList();
    }

    public TaskResponse updateTask(Task task) {
        Task existingTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new NotFoundException("Task id not found: " + task.getId()));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());
        return new TaskResponse(
                existingTask.getId(),
                existingTask.getTitle(),
                existingTask.getDescription(),
                existingTask.getStatus(),
                existingTask.getPriority(),
                existingTask.getDueDate()
        );
    }

    public void deleteTask(Long userId, Long taskId) {

        if (!taskRepository.existsByUserIdAndId(userId, taskId)) {
            throw new NotFoundException("Task id not found: " + taskId + " for user id: " + userId);
        }
        taskRepository.deleteByUserIdAndId(userId, taskId);
    }

    public List<TaskResponse> filterTasks(String status, Long userId) {
        return taskRepository.filterTasks(status, userId)
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDueDate()
                ))
                .toList();
    }
    
}

package com.nitinson.app.taskchecker.rest;

import com.nitinson.app.taskchecker.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository repository;

    @Autowired
    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Long> createTask(@RequestBody TaskDto taskDto) {
        Task task = new Task(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());

        Task savedTask = repository.save(task);
        return ResponseEntity.ok(savedTask.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        Optional<Task> optionalTask = repository.findById(id);
        return optionalTask.map(task -> ResponseEntity.ok(task.toDto()))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody TaskDto updatedTaskDto) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            String statusString = updatedTaskDto.getStatus();

            if (isValidTaskStatus(statusString)){
                TaskStatus taskStatus = TaskStatus.valueOf(statusString);
                task.setTitle(updatedTaskDto.getTitle());
                task.setDescription(updatedTaskDto.getDescription());
                task.setTaskStatus(taskStatus);
                TaskDto savedDto = repository.save(task).toDto();
                return ResponseEntity.ok(savedDto.toString());
            } else {
                return ResponseEntity.badRequest().body("Available statuses are: CREATED, APPROVED, REJECTED, BLOCKED, DONE.");
            }
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // helper method to check if statusString matches a valid TaskStatus enum value
    private boolean isValidTaskStatus(String statusString) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.name().equals(statusString)) {
                return true;
            }
        }
        return false;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        Iterable<Task> tasks = repository.findAll();
        List<TaskDto> taskDtos = StreamSupport.stream(tasks.spliterator(), false)
                .map(Task::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(taskDtos);
    }

}

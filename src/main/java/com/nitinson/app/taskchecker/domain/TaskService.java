package com.nitinson.app.taskchecker.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task addNewTask(TaskDto taskDto) {
        Task task = new Task(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());

        return taskRepository.save(task);
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    public TaskDto updateTask(Task task) {
        return taskRepository.save(task).toDto();
    }

    public boolean taskExists(Long id) {
        return taskRepository.existsById(id);
    }

    public void deleteTasks(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}

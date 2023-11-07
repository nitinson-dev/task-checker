package com.nitinson.app.taskchecker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static com.nitinson.app.taskchecker.domain.TaskStatus.CREATED;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private TaskStatus status = CREATED;

    public Task(String title) {
        this.title = title;
    }

    public Task() {}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public TaskDto toDto() {
        return new TaskDto(String.valueOf(id), title, description, status.name());
    }
}

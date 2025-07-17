package com.aasencios.taskapi.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class TaskDTO {

    private Long id;

    @NotBlank(message = "El titulo es obligatorio")
    private String title;

    @NotBlank(message = "La descripcion es obligatoria")
    private String description;

    private Boolean completed;

    @Temporal(TemporalType.DATE)
    private LocalDate dueDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

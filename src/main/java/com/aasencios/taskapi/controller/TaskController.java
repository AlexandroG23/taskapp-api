package com.aasencios.taskapi.controller;

import com.aasencios.taskapi.dto.TaskDTO;
import com.aasencios.taskapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO dto, Authentication authentication){
        String email = authentication.getName();
        TaskDTO created = taskService.createTask(dto, email);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(Authentication authentication) {
        String email = authentication.getName();
        List<TaskDTO> tasks = taskService.getAllTasks(email);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@Valid @PathVariable Long id, @RequestBody TaskDTO dto, Authentication authentication) {
        String email = authentication.getName();
        TaskDTO updated = taskService.updateTask(id, dto, email);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        taskService.deleteTask(id, email);
        return ResponseEntity.noContent().build();
    }
}

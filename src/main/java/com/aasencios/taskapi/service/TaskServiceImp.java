package com.aasencios.taskapi.service;

import com.aasencios.taskapi.exception.TaskNotFoundException;
import com.aasencios.taskapi.dto.TaskDTO;
import com.aasencios.taskapi.model.Task;
import com.aasencios.taskapi.model.User;
import com.aasencios.taskapi.repository.TaskRepository;
import com.aasencios.taskapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImp(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO createTask(TaskDTO dto, String userEmail) {
        User user = getUserByEmail(userEmail);

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        task.setDueDate(dto.getDueDate());
        task.setUser(user);

        Task saved = taskRepository.save(task);
        return mapToDTO(saved);
    }


    @Override
    public List<TaskDTO> getAllTasks(String userEmail) {
        User user = getUserByEmail(userEmail);

        List<Task> tasks = taskRepository.findByUser(user);
        return tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO dto, String userEmail) {
        User user = getUserByEmail(userEmail);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada con ID: " + taskId));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No se puede modificar esta tarea");
        }

        // Solo actualiza si los campos no son null
        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }

        if (dto.getCompleted() != null) {
            task.setCompleted(dto.getCompleted());
        }

        if (dto.getDueDate() != null) {
            task.setDueDate(dto.getDueDate());
        }

        if (dto.getCompleted() != null && dto.getCompleted() && task.getDueDate() != null) {
            if (task.getDueDate().isBefore(LocalDate.now())) {
                throw new IllegalStateException("No se puede completar una tarea vencida.");
            }
        }

        Task updated = taskRepository.save(task);
        return mapToDTO(updated);
    }


    @Override
    public void deleteTask(Long taskId, String userEmail) {
        User user = getUserByEmail(userEmail);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada con ID: "+taskId));

        if (!task.getUser().getId().equals(user.getId())){
            throw new RuntimeException("No tienes tareas que eliminar esta tarea");
        }

        taskRepository.delete(task);
    }

    private User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private TaskDTO mapToDTO(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        dto.setDueDate(task.getDueDate());

        return dto;
    }
}

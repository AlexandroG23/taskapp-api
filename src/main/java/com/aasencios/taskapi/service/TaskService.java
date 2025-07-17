package com.aasencios.taskapi.service;

import com.aasencios.taskapi.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(TaskDTO dto, String userEmail);

    List<TaskDTO> getAllTasks(String userEmail);

    TaskDTO updateTask(Long taskId, TaskDTO dto, String userEmail);

    void deleteTask(Long taskId, String userEmail);
}

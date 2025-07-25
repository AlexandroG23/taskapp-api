package com.aasencios.taskapi.repository;

import com.aasencios.taskapi.model.Task;
import com.aasencios.taskapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
}

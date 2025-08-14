package com.IvanAndonov1.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IvanAndonov1.taskmanager.model.Task;
import com.IvanAndonov1.taskmanager.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}

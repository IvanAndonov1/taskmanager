package com.IvanAndonov1.taskmanager.service;

import java.util.List;

import com.IvanAndonov1.taskmanager.model.Task;
import com.IvanAndonov1.taskmanager.model.User;

public interface TaskService {
    List<Task> findAllByUser(User user);
    Task save(Task task, User user);
    Task update(Long id, Task updatedTask, User user);
    void delete(Long id, User user);
}

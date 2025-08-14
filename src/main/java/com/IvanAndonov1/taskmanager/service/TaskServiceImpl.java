package com.IvanAndonov1.taskmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IvanAndonov1.taskmanager.model.Task;
import com.IvanAndonov1.taskmanager.model.User;
import com.IvanAndonov1.taskmanager.repository.TaskRepository;


@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAllByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Override
        public Task save(Task task, User user) {
            logger.info("Saving task '{}' for user '{}'", task.getTitle(), user.getUsername());
            task.setUser(user);
            return taskRepository.save(task);
        }

    @Override
    public Task update(Long id, Task updatedTask, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(task);
    }

    @Override
    public void delete(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        taskRepository.delete(task);
    }
}

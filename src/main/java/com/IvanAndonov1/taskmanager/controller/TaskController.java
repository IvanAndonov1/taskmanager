package com.IvanAndonov1.taskmanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IvanAndonov1.taskmanager.model.Task;
import com.IvanAndonov1.taskmanager.model.User;
import com.IvanAndonov1.taskmanager.repository.UserRepository;
import com.IvanAndonov1.taskmanager.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    @GetMapping
    public List<Task> getAllTasks() {
        User user = getCurrentUser();
        logger.info("Fetching all tasks for user: {}", user.getUsername());
        return taskService.findAllByUser(user);
    }


    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task, getCurrentUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.update(id, task, getCurrentUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.delete(id, getCurrentUser());
        return ResponseEntity.ok().build();
    }
}

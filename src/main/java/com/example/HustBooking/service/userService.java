package com.example.HustBooking.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HustBooking.dto.UpdateUserRequest;
import com.example.HustBooking.model.userModel;
import com.example.HustBooking.repository.userRepository;

import jakarta.transaction.Transactional;

@Service
public class userService {

    @Autowired
    private userRepository repository;

    // Create
    public Optional<userModel> addUser(userModel model) {
        // Check if username already exists
        if (repository.existsByUsername(model.getUsername())) {
            return Optional.empty();
        }
        repository.save(model);
        return Optional.of(model);
    }

    // Read operations
    public List<userModel> getAllUsers() {
        return repository.findAll();
    }

    public Optional<userModel> getUserById(Long id) {
        return repository.findById(id);
    }

    public Optional<userModel> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public List<userModel> searchUsers(String keyword) {
        return repository.searchUsers(keyword);
    }

    public List<userModel> getUsersByPassword(String password) {
        return repository.findUserByPassword(password);
    }

    // Update
    @Transactional
    public boolean updateUser(UpdateUserRequest request) {
        Optional<userModel> userOpt = repository.findById(request.getId());
        if (userOpt.isPresent()) {
            userModel user = userOpt.get();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            repository.save(user);
            return true;
        }
        return false;
    }

    // Delete
    @Transactional
    public boolean deleteUser(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

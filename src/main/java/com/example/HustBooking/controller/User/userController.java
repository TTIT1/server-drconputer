package com.example.HustBooking.controller.User;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.HustBooking.model.userModel;
import com.example.HustBooking.service.userService;
import com.example.HustBooking.dto.UpdateUserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "User Management", description = "User management APIs")
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class userController {

    @Autowired
    private userService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "409", description = "Username already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody userModel user) {
        try {
            Optional<userModel> savedUser = userService.addUser(user);
            if (savedUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.get());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error creating user: " + e.getMessage());
        }
    }

    @Operation(summary = "Get  alll users   " , description = "Retrieves all users  ")
    @GetMapping()
    public ResponseEntity<List<userModel>> getAllUsers() {
               
        try
        {
            
            List<userModel> geList = userService.getAllUsers();
            return ResponseEntity.ok(geList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

   
    @Operation(summary = "Search users", description = "Search users by keyword in username or password")
    @GetMapping("/search")
    public ResponseEntity<List<userModel>> searchUsers(
        @Parameter(description = "Keyword to search for") @RequestParam String keyword) {
        try {
            return ResponseEntity.ok(userService.searchUsers(keyword));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get user by username", description = "Retrieves a user by their username")
    @GetMapping("/by-username")
    public ResponseEntity<?> getUserByUsername(
        @Parameter(description = "Username to search for") @RequestParam String username) {
        try {
            return userService.getUserByUsername(username)
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get users by password", description = "Retrieves users by their password")
    @GetMapping("/by-password")
    public ResponseEntity<List<userModel>> getUsersByPassword(
        @Parameter(description = "Password to search for") @RequestParam String password) {
        try {
            return ResponseEntity.ok(userService.getUsersByPassword(password));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update user", description = "Updates an existing user's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest request) {
        try {
            boolean updated = userService.updateUser(request);
            if (updated) {
                return ResponseEntity.ok("User updated successfully");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error updating user: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
        @Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error deleting user: " + e.getMessage());
        }
    }
}

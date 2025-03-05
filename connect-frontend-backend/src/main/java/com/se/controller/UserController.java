package com.se.controller;

import com.se.model.ApiResponse;
import com.se.model.User;
import com.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend connections
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers(); // Return the list of users directly
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id); // Return the user directly
    }

    // Get a user by Email
    @GetMapping("/email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email); // Return the user directly
    }

    // Create a new user
    @PostMapping
    public ApiResponse createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ApiResponse("User created successfully", true);
    }

    @PostMapping(value = "/login")
    public ApiResponse login(@RequestBody User user) {
        try {
            User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
            // Return user details as a response
            return new ApiResponse("Login successful", true, loggedInUser);
        } catch (RuntimeException e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }


    @PostMapping(value = "/signup")
    public ApiResponse signUp(@RequestBody User user) {
        try {
            User registeredUser = userService.signUpUser(user);
            return new ApiResponse("User registered successfully", true);
        } catch (RuntimeException e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ApiResponse updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return new ApiResponse("User updated successfully", true);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ApiResponse("User deleted successfully", true);
    }
}

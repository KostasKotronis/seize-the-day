package com.seizetheday.backend.controller;

import com.seizetheday.backend.domain.User;
import com.seizetheday.backend.dto.ApiResponse;
import com.seizetheday.backend.dto.UserRequest;
import com.seizetheday.backend.dto.UserResponse;
import com.seizetheday.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserRequest u) {
        return userService.create(u);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@RequestParam String username) {
            return userService.findByUsername(username);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> replace(@PathVariable Long id, @Valid @RequestBody UserRequest u) {
        return userService.replace(id, u);
    }


}

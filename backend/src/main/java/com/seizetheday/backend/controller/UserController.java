package com.seizetheday.backend.controller;

import com.seizetheday.backend.dto.ApiResponse;
import com.seizetheday.backend.dto.UserRequest;
import com.seizetheday.backend.dto.UserResponse;
import com.seizetheday.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserRequest u) {
        return userService.create(u);
    }

}

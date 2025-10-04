package com.seizetheday.backend.service;

import com.seizetheday.backend.domain.User;
import com.seizetheday.backend.dto.ApiResponse;
import com.seizetheday.backend.dto.UserRequest;
import com.seizetheday.backend.dto.UserResponse;
import com.seizetheday.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<ApiResponse<UserResponse>> create(UserRequest request) {
        HttpStatusCode status = HttpStatus.CREATED;


        //create entity to save
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());


        //init api response
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setTimestamp(Instant.now());
        apiResponse.setSuccess(true);
        try {
            User userRes = userRepository.save(user);
            UserResponse userResponse = new UserResponse(
                    userRes.getId(),
                    userRes.getUsername(),
                    userRes.getRoles());
            apiResponse.setData(userResponse);
            apiResponse.setMessage("User created successfully!");
        } catch (DataIntegrityViolationException ex) {
            status = HttpStatus.CONFLICT;
            apiResponse.setSuccess(false);
            apiResponse.setMessage("An error occurred upon user creation!");
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Internal server error!");
        }

        return ResponseEntity
                .status(status)
                .body(apiResponse);
    }


    public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {
        HttpStatusCode status = HttpStatus.OK;

        //init api response
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setTimestamp(Instant.now());
        apiResponse.setSuccess(true);

        try {
            List<UserResponse> userResponse = userRepository
                    .findAll()
                    .stream()
                    .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getRoles()))
                    .toList();
            apiResponse.setData(userResponse);
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Internal server error!");
        }

        return ResponseEntity
                .status(status)
                .body(apiResponse);
    }

    public ResponseEntity<ApiResponse<UserResponse>> findById(Long id) {
        HttpStatusCode status = HttpStatus.OK;

        //init api response
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setTimestamp(Instant.now());
        apiResponse.setSuccess(true);

        try {
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()) {
                UserResponse userResponse = new UserResponse(
                        user.get().getId(),
                        user.get().getUsername(),
                        user.get().getRoles());
                apiResponse.setData(userResponse);
            } else {
                apiResponse.setMessage("No user found!");
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Internal server error!");
        }

        return ResponseEntity
                .status(status)
                .body(apiResponse);
    }

    public ResponseEntity<ApiResponse<UserResponse>> findByUsername(String username) {
        HttpStatusCode status = HttpStatus.OK;

        //init api response
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setTimestamp(Instant.now());
        apiResponse.setSuccess(true);

        try {
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isPresent()) {
                UserResponse userResponse = new UserResponse(
                        user.get().getId(),
                        user.get().getUsername(),
                        user.get().getRoles());
                apiResponse.setData(userResponse);
            } else {
                apiResponse.setMessage("No user found!");
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Internal server error!");
        }
        return ResponseEntity
                .status(status)
                .body(apiResponse);
    }

    @Transactional
    public ResponseEntity<ApiResponse<UserResponse>> replace(Long id, UserRequest request) {
        HttpStatusCode status = HttpStatus.OK;

        //init api response
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setTimestamp(Instant.now());
        apiResponse.setSuccess(true);

        Optional<User> userOld = userRepository.findById(id);
        if(userOld.isPresent()) {
            User uOld = userOld.get();
            uOld.setUsername(request.getUsername());
            uOld.setPassword(passwordEncoder.encode(request.getPassword()));
            uOld.setRoles(request.getRoles());

            UserResponse userResponse = new UserResponse(
                    uOld.getId(),
                    uOld.getUsername(),
                    uOld.getRoles());
            apiResponse.setData(userResponse);
        } else {
            apiResponse.setMessage("No user found!");
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
        .status(status)
        .body(apiResponse);
    }

    @Transactional
    public ResponseEntity<ApiResponse<UserResponse>> deleteById(Long id) {
        HttpStatusCode status = HttpStatus.OK;

        //init api response
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setTimestamp(Instant.now());
        apiResponse.setSuccess(true);

        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
            } else {
                apiResponse.setMessage("No user found!");
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Internal server error!");
        }
        return ResponseEntity
                .status(status)
                .body(apiResponse);
    }

}

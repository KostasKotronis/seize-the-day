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
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse<UserResponse>> create(UserRequest request) {
        HttpStatusCode status = HttpStatus.CREATED;

        //create entity to save
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRoles(request.getRoles());

        //init user response
        UserResponse userResponse = new UserResponse();

        //init api response
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setTimestamp(Instant.now());
        apiResponse.setSuccess(true);
        try {
            User userRes = userRepository.save(user);
            userResponse.setUsername(userRes.getUsername());
            userResponse.setRoles(userRes.getRoles());
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

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional <User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional <User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}

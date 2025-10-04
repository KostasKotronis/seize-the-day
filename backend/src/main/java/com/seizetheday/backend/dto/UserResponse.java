package com.seizetheday.backend.dto;

import com.seizetheday.backend.domain.Role;
import com.seizetheday.backend.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Setter
@Getter
public class UserResponse {
    private Long id;
    private String username;
    private Set<Role> roles;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

}

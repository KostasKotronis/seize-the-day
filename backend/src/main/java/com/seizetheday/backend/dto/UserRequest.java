package com.seizetheday.backend.dto;

import com.seizetheday.backend.domain.Role;
import com.seizetheday.backend.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserRequest {
    private String username;
    private String password;
    private Set<Role> roles;
}

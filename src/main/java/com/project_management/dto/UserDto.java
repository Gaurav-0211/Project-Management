package com.project_management.dto;

import com.project_management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private Role role;

}

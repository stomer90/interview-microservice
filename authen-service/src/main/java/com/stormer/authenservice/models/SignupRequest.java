package com.stormer.authenservice.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    private Set<String> role;
}

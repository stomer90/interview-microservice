package com.stormer.authenservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString(exclude = {"password"})
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequest {
    private String username = "";
    private String password = "";
}

package com.stormer.authenservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {
    @JsonProperty("Fullname")
    private String name;
    @JsonProperty("Username")
    private String username;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
}

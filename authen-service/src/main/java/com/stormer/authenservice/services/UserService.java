package com.stormer.authenservice.services;

import com.stormer.authenservice.models.UserProfileResponse;

public interface UserService {
    UserProfileResponse getUserProfile(String username);
}

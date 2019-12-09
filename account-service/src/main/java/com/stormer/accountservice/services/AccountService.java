package com.stormer.accountservice.services;

import com.stormer.accountservice.models.User;

import java.util.Optional;

public interface AccountService {
    Optional<User> getUserProfile(String username);
}

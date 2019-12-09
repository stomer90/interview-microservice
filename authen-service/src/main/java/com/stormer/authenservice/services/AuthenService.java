package com.stormer.authenservice.services;

import com.stormer.authenservice.models.SigninRequest;
import com.stormer.authenservice.models.SigninResponse;
import com.stormer.authenservice.models.SignupRequest;
import com.stormer.authenservice.models.SignupResponse;

import java.util.Set;

public interface AuthenService {
    SigninResponse signin(SigninRequest request);
    SignupResponse signup(SignupRequest response, Set<String> role);
}

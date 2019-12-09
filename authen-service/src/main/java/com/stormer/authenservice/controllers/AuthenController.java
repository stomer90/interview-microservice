package com.stormer.authenservice.controllers;

import com.stormer.accountservice.grpc.user.User;
import com.stormer.authenservice.constants.AuthenConstants;
import com.stormer.authenservice.grpc.GrpcConnect;
import com.stormer.authenservice.models.SigninResponse;
import com.stormer.authenservice.models.SignupRequest;
import com.stormer.authenservice.models.SigninRequest;
import com.stormer.authenservice.models.SignupResponse;
import com.stormer.authenservice.services.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenController {

    @Autowired
    AuthenService authenService;

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@Valid @RequestBody SigninRequest request){
        SigninResponse response = authenService.signin(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signUp(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authenService.signup(request, Collections.singleton(AuthenConstants.SIGNUP_USER));

        return ResponseEntity.ok().body(response);
    }
}

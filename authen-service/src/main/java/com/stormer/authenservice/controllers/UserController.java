package com.stormer.authenservice.controllers;

import com.stormer.accountservice.grpc.user.User;
import com.stormer.authenservice.grpc.GrpcConnect;
import com.stormer.authenservice.models.UserProfileResponse;
import com.stormer.authenservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserProfile(username));
    }
}

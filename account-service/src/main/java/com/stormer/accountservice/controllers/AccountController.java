package com.stormer.accountservice.controllers;

import com.stormer.accountservice.models.User;
import com.stormer.accountservice.services.AccountService;
import com.stormer.common.exception.DemoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserProfile(@PathVariable("username") String username){
        return ResponseEntity.ok().body(accountService.getUserProfile(username).orElseThrow(() -> new DemoRuntimeException("sdf", "sdfsd")));
    }
}

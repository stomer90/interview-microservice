package com.stormer.authenservice.services.impl;

import com.stormer.accountservice.grpc.user.User;
import com.stormer.accountservice.grpc.user.UserRequest;
import com.stormer.authenservice.grpc.GrpcConnect;
import com.stormer.authenservice.models.UserProfileResponse;
import com.stormer.authenservice.securities.jwt.JwtProvider;
import com.stormer.authenservice.services.UserService;
import com.stormer.common.constants.CodeConstants;
import com.stormer.common.exception.DemoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl extends GrpcConnect implements UserService {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    HttpServletRequest request;

    @Override
    public UserProfileResponse getUserProfile(String username) {
        checkNotMapUsername(username);

        UserRequest request = UserRequest.newBuilder().setUsername(username).build();

        try {
            // Call Grpc to get User by username
            User user = userServiceBlockingStub.getUserProfile(request);

            return UserProfileResponse.builder().name(user.getName()).username(user.getUsername()).phoneNumber(user.getPhoneNumber()).build();
        } catch (Exception ex) {
            throw new DemoRuntimeException(CodeConstants.NOT_FOUND, "No users found");
        }
    }

    private void checkNotMapUsername(String username) {
        String token = jwtProvider.getJwt(request);
        if (!username.equalsIgnoreCase(jwtProvider.getUsernameFromJwtToken(token))) {
            throw new DemoRuntimeException(CodeConstants.NOT_FOUND, "No users found");
        }
    }
}

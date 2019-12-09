package com.stormer.authenservice.services;

import com.stormer.authenservice.securities.jwt.JwtProvider;
import com.stormer.authenservice.services.impl.UserServiceImpl;
import com.stormer.common.exception.DemoRuntimeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    JwtProvider jwtProvider;

    @Mock
    HttpServletRequest httpServletRequest;

//    @Mock
//    UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = DemoRuntimeException.class)
    public void getUserProfile_TokenInvalid() {
        String username = "anv123";

        when(jwtProvider.getJwt(httpServletRequest)).thenReturn("Token");
        when(jwtProvider.getUsernameFromJwtToken(anyString())).thenReturn("sdf");
        userService.getUserProfile(username);
    }

//    @Test
//    public void getUserProfile() {
//        String username = "anv123";
//        userServiceBlockingStub = PowerMockito.mock(UserServiceGrpc.UserServiceBlockingStub.class);
//
//        PowerMockito.when(jwtProvider.getJwt(httpServletRequest)).thenReturn("Token");
//        PowerMockito.when(jwtProvider.validateJwtToken(anyString())).thenReturn(true);
//        PowerMockito.when(jwtProvider.getUsernameFromJwtToken(anyString())).thenReturn(username);
//        PowerMockito.when(userServiceBlockingStub.getUserProfile(any(UserRequest.class))).thenReturn(User.newBuilder().setName("Nguyen Van A").setUsername(username).setPhoneNumber("+84979647690").build());
//
//        userService.getUserProfile(username);
//    }
}

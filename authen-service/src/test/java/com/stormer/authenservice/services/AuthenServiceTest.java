package com.stormer.authenservice.services;

import com.stormer.authenservice.constants.AuthenConstants;
import com.stormer.authenservice.entities.Role;
import com.stormer.authenservice.enums.RoleName;
import com.stormer.authenservice.models.SigninRequest;
import com.stormer.authenservice.models.SigninResponse;
import com.stormer.authenservice.models.SignupRequest;
import com.stormer.authenservice.models.SignupResponse;
import com.stormer.authenservice.repositories.RoleRepository;
import com.stormer.authenservice.repositories.UserRepository;
import com.stormer.authenservice.securities.jwt.JwtProvider;
import com.stormer.authenservice.services.impl.AuthenServiceImpl;
import com.stormer.common.exception.DemoRuntimeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    private AuthenService authenService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authenService = new AuthenServiceImpl(authenticationManager, jwtProvider, userRepository, roleRepository, passwordEncoder);
    }

    @Test(expected = DemoRuntimeException.class)
    public void signup_UsernameExisted() {

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        authenService.signup(mockSignupRequest(), Collections.singleton(AuthenConstants.SIGNUP_USER));
    }

    @Test(expected = DemoRuntimeException.class)
    public void signup_PhoneNumberExisted() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(anyString())).thenReturn(true);

        authenService.signup(mockSignupRequest(), Collections.singleton(AuthenConstants.SIGNUP_USER));
    }

    @Test
    public void signup_success() {
        SignupRequest request = mockSignupRequest();
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("11223344");
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(new Role()));
        when(roleRepository.findByName(RoleName.ROLE_ADMIN)).thenReturn(Optional.of(new Role()));

        SignupResponse response = authenService.signup(request, Collections.singleton(AuthenConstants.SIGNUP_USER));
        Assert.assertEquals(response.getUsername(), request.getUsername());

        response = authenService.signup(request, Arrays.asList(AuthenConstants.SIGNUP_ADMIN, AuthenConstants.SIGNUP_USER).stream().collect(Collectors.toSet()));
        Assert.assertEquals(response.getUsername(), request.getUsername());
    }

    @Test
    public void signin() {
        SigninRequest request = new SigninRequest();
        request.setUsername("anv123");
        request.setPassword("123456");
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        when(jwtProvider.generateJwtToken(authentication)).thenReturn("Token");

        SigninResponse response = authenService.signin(request);
        Assert.assertEquals(response.getToken(), "Token");
    }


    private SignupRequest mockSignupRequest() {
        SignupRequest request = new SignupRequest();
        request.setName("Nguyen Van A");
        request.setUsername("anv123");
        request.setPhoneNumber("+84979647690");
        request.setPassword("123456");

        return request;
    }
}

package com.stormer.authenservice.services.impl;

import com.stormer.authenservice.entities.Role;
import com.stormer.authenservice.entities.User;
import com.stormer.authenservice.enums.RoleName;
import com.stormer.authenservice.models.SigninRequest;
import com.stormer.authenservice.models.SigninResponse;
import com.stormer.authenservice.models.SignupRequest;
import com.stormer.authenservice.models.SignupResponse;
import com.stormer.authenservice.repositories.RoleRepository;
import com.stormer.authenservice.repositories.UserRepository;
import com.stormer.authenservice.securities.jwt.JwtProvider;
import com.stormer.authenservice.services.AuthenService;
import com.stormer.common.constants.CodeConstants;
import com.stormer.common.exception.DemoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class AuthenServiceImpl implements AuthenService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRepository userRepository,
                             RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SigninResponse signin(SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateJwtToken(authentication);

        return SigninResponse.builder().token(token).build();
    }

    @Override
    public SignupResponse signup(SignupRequest request, Set<String> strRoles) {
        validateExisted(request);

        // Creating user
        User user = new User(request.getName(), request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getPhoneNumber());
        Set<Role> roles = new HashSet<>();

        strRoles.stream().forEach(role -> assignRole(roles, role));

        user.setRoles(roles);
        userRepository.save(user);

        return SignupResponse.builder().username(request.getUsername()).build();
    }

    private void assignRole(Set<Role> roles, String role) {
        Supplier<DemoRuntimeException> runtimeException = () -> new DemoRuntimeException(CodeConstants.NOT_FOUND, "Cause: User Role not find.");
        switch(role) {
            case "admin":
                Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(runtimeException);
                roles.add(adminRole);

                break;
            default:
                Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(runtimeException);
                roles.add(userRole);
        }
    }

    private void validateExisted(SignupRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new DemoRuntimeException(CodeConstants.NOT_FOUND, "Username is already in use!");
        }

        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DemoRuntimeException(CodeConstants.NOT_FOUND, "PhoneNumber is already in use!");
        }
    }


}

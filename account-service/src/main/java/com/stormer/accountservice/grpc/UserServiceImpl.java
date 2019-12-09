package com.stormer.accountservice.grpc;

import com.stormer.accountservice.grpc.user.User;
import com.stormer.accountservice.grpc.user.UserRequest;
import com.stormer.accountservice.grpc.user.UserServiceGrpc;
import com.stormer.accountservice.grpc.user.UserServiceGrpc.UserServiceImplBase;
import com.stormer.accountservice.services.AccountService;
import com.stormer.common.constants.CodeConstants;
import com.stormer.common.exception.DemoRuntimeException;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class UserServiceImpl extends UserServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    AccountService accountService;

    @Override
    public void getUserProfile(UserRequest request, StreamObserver<User> responseObserver) {

        com.stormer.accountservice.models.User user = accountService.getUserProfile(request.getUsername()).orElseThrow(() -> new DemoRuntimeException(CodeConstants.NOT_FOUND, "User not found"));
        User result = User.newBuilder().setId(user.getId()).setName(user.getName()).setUsername(user.getUsername()).setPhoneNumber(user.getPhoneNumber()).build();

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}

package com.stormer.authenservice.grpc;

import com.stormer.accountservice.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GrpcConnect {
    protected UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Value("${stormer.grpc.host}")
    private String grpcHost;

    @Value("${stormer.grpc.port}")
    private int grpcPort;

    @PostConstruct
    private void init() {
        // Connect to GRPC server
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort).usePlaintext().build();
        userServiceBlockingStub = UserServiceGrpc.newBlockingStub(managedChannel);
    }

}

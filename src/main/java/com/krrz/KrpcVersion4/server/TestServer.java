package com.krrz.KrpcVersion4.server;


import com.krrz.KrpcVersion4.service.BlogService;
import com.krrz.KrpcVersion4.service.UserService;
import com.krrz.KrpcVersion4.service.impl.BlogServiceImpl;
import com.krrz.KrpcVersion4.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
package com.krrz.KrpcVersion5.server;

import com.krrz.KrpcVersion5.service.BlogService;
import com.krrz.KrpcVersion5.service.UserService;
import com.krrz.KrpcVersion5.service.impl.BlogServiceImpl;
import com.krrz.KrpcVersion5.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8899);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
package com.krrz.KrpcVersion2.server;


import com.krrz.KrpcVersion2.service.BlogService;
import com.krrz.KrpcVersion2.service.UserService;
import com.krrz.KrpcVersion2.service.impl.BlogServiceImpl;
import com.krrz.KrpcVersion2.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.addServiceInterface(userService);
        serviceProvider.addServiceInterface(blogService);

        RPCServer RPCServer = new ThreadPoolRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
package com.krrz.KrpcVersion6.server;


import com.krrz.KrpcVersion6.service.BlogService;
import com.krrz.KrpcVersion6.service.UserService;
import com.krrz.KrpcVersion6.service.impl.BlogServiceImpl;
import com.krrz.KrpcVersion6.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestServer2 {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8900);
        // System.out.println("hahah");
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);
        RPCServer RPCServer = new NettyRPCServer(serviceProvider);

        RPCServer.start(8900);
    }
}

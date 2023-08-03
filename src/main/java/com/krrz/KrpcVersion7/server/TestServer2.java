package com.krrz.KrpcVersion7.server;


import com.krrz.KrpcVersion7.service.BlogService;
import com.krrz.KrpcVersion7.service.UserService;
import com.krrz.KrpcVersion7.service.impl.BlogServiceImpl;
import com.krrz.KrpcVersion7.service.impl.UserServiceImpl;

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

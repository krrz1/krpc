package com.krrz.KrpcVersion3.server;

import com.krrz.KrpcVersion3.service.BlogService;
import com.krrz.KrpcVersion3.service.UserService;
import com.krrz.KrpcVersion3.service.impl.BlogServiceImpl;
import com.krrz.KrpcVersion3.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String args[]){
        UserService userService=new UserServiceImpl();
        BlogService blogService=new BlogServiceImpl();

        ServiceProvider serviceProvider=new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer rpcServer=new NettyRPCServer(serviceProvider);
        rpcServer.start(8899);

    }
}

package com.krrz.KrpcVersion8.server;

import com.krrz.KrpcVersion8.annotation.KrpcServer;
import com.krrz.KrpcVersion8.service.BlogService;
import com.krrz.KrpcVersion8.service.UserService;
import com.krrz.KrpcVersion8.service.impl.BlogServiceImpl;
import com.krrz.KrpcVersion8.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.krrz.KrpcVersion8.service")
public class TestServer {
    public static void main(String[] args) {
        //启动spring容器
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(TestServer.class);
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8899,applicationContext);
        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
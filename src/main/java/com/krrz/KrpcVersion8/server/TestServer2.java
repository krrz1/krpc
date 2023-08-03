package com.krrz.KrpcVersion8.server;

import com.krrz.KrpcVersion8.service.BlogService;
import com.krrz.KrpcVersion8.service.UserService;
import com.krrz.KrpcVersion8.service.impl.BlogServiceImpl;
import com.krrz.KrpcVersion8.service.impl.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.krrz.KrpcVersion8.service")
public class TestServer2 {
    public static void main(String[] args) {
//        UserService userService = new UserServiceImpl();
//        BlogService blogService = new BlogServiceImpl();

        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(TestServer2.class);

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8900,applicationContext);
//        serviceProvider.provideServiceInterface(userService);
//        serviceProvider.provideServiceInterface(blogService);
        RPCServer RPCServer = new NettyRPCServer(serviceProvider);

        RPCServer.start(8900);
    }
}

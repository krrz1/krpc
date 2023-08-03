package com.krrz.KrpcVersion3.client;

import com.krrz.KrpcVersion3.domain.Blog;
import com.krrz.KrpcVersion3.domain.User;
import com.krrz.KrpcVersion3.service.BlogService;
import com.krrz.KrpcVersion3.service.UserService;

import java.sql.Statement;

public class TestClient {
    private static int type=1;
    public static void main(String args[]){
        switch (type){
            case 0:{
                // 构建一个使用java Socket传输的客户端
                SimpleRPCClient simpleRPCClient = new SimpleRPCClient("127.0.0.1", 8899);
                // 把这个客户端传入代理客户端
                RPCClientProxy rpcClientProxy = new RPCClientProxy(simpleRPCClient);
                // 代理客户端根据不同的服务，获得一个代理类， 并且这个代理类的方法以或者增强（封装数据，发送请求）
                UserService userService = rpcClientProxy.getProxy(UserService.class);
                // 调用方法
                User userByUserId = userService.getUserByUserId(10);
                break;
            }
            case 1:{
                // 构建一个使用java Socket/ netty/....传输的客户端
                RPCClient rpcClient = new NettyRPCClient("127.0.0.1", 8899);
                // 把这个客户端传入代理客户端
                RPCClientProxy rpcClientProxy = new RPCClientProxy(rpcClient);
                // 代理客户端根据不同的服务，获得一个代理类， 并且这个代理类的方法以或者增强（封装数据，发送请求）
                UserService userService = rpcClientProxy.getProxy(UserService.class);
                // 调用方法
                User userByUserId = userService.getUserByUserId(10);
                System.out.println("从服务端得到的user为：" + userByUserId);

                User user = User.builder().userName("张三").id(100).sex(true).build();
                Integer integer = userService.insertUserId(user);
                System.out.println("向服务端插入数据："+integer);

                BlogService blogService = rpcClientProxy.getProxy(BlogService.class);

                Blog blogById = blogService.getBlogById(10000);
                System.out.println("从服务端得到的blog为：" + blogById);
                break;
            }
        }

    }
}

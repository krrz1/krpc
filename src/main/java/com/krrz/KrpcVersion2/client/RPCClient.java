package com.krrz.KrpcVersion2.client;

import com.krrz.KrpcVersion2.domain.Blog;
import com.krrz.KrpcVersion2.domain.User;
import com.krrz.KrpcVersion2.service.BlogService;
import com.krrz.KrpcVersion2.service.UserService;

public class RPCClient {
    public static void main(String args[]) {
        ProxyClient proxyClient = new ProxyClient("127.0.0.1", 8899);
        UserService userService = proxyClient.getProxy(UserService.class);

        User userByUserId = userService.getUserByUserId(10);
        System.out.println("从服务端得到的user为：" + userByUserId);

        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = userService.insertUserId(user);
        System.out.println("向服务端插入数据：" + integer);

        BlogService blogService=proxyClient.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(100);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
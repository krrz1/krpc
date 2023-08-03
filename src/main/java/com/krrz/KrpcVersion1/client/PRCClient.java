package com.krrz.KrpcVersion1.client;

import com.krrz.KrpcVersion1.domain.User;
import com.krrz.KrpcVersion1.service.UserService;

import javax.jws.soap.SOAPBinding;
import java.lang.reflect.Proxy;

public class PRCClient {
    public static void main(String args[]){
        ProxyClient proxyClient=new ProxyClient("127.0.0.1",8899);
        UserService userService=proxyClient.getProxy(UserService.class);

        //服务1的方法
        User user=userService.getUserByUserId(10);
        System.out.println("从服务端得到的user为："+user);
        //服务2的方法
        User user1= User.builder().userName("张三").id(100).sex(true).build();
        Integer integer=userService.insertUserId(user);
        System.out.println("向服务端插入数据："+integer);
    }
}

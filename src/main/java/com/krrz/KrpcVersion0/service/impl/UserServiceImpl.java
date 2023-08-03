package com.krrz.KrpcVersion0.service.impl;

import com.krrz.KrpcVersion0.domain.User;
import com.krrz.KrpcVersion0.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(int id) {
        System.out.println("客户端查询你了id为"+id+"的用户");
        Random random=new Random();
        User user=User.builder().userName(UUID.randomUUID().toString()).sex(random.nextBoolean()).id(id).build();
        return user;
    }
}

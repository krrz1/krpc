package com.krrz.KrpcVersion7.service;


import com.krrz.KrpcVersion7.domain.User;

public interface UserService {
    // 客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);

    Integer insertUserId(User user);
}

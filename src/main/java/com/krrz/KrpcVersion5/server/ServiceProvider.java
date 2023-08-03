package com.krrz.KrpcVersion5.server;

import com.krrz.KrpcVersion5.register.ServiceRegister;
import com.krrz.KrpcVersion5.register.ZkServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类
 * 服务启动时要暴露其相关的实现类
 * 根据request中的interface调用服务端中相关实现类
 */
public class ServiceProvider {
    /**
     * 一个实现类可能实现多个服务接口，
     */
    private Map<String, Object> interfaceProvider;

    private ServiceRegister serviceRegister;
    private String host;
    private int port;

    public ServiceProvider(String host,int port){
        //传入自身的网络地址 用于注册
        this.host=host;
        this.port=port;
        this.serviceRegister=new ZkServiceRegister();
        this.interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();

        for(Class clazz : interfaces){
            //本机映射表
            interfaceProvider.put(clazz.getName(),service);
            //去注册中心注册服务
            serviceRegister.register(clazz.getName(),new InetSocketAddress(host,port));
        }

    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}

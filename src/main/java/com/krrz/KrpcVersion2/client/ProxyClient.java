package com.krrz.KrpcVersion2.client;

import com.krrz.KrpcVersion1.domain.RPCRequest;
import com.krrz.KrpcVersion1.domain.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class ProxyClient implements InvocationHandler {
    //转入service接口的class对象，反射封装成一个request
    private String host;
    private int port;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request=RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .paramsTypes(method.getParameterTypes())
                .params(args).build();
        RPCResponse response= IOClient.sendRequest(host,port,request);

        return response.getData();
    }

    <T>T getProxy(Class<T> clazz){
        Object o= Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
        return (T) o;
    }
}

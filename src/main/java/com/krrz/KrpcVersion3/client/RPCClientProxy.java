package com.krrz.KrpcVersion3.client;

import com.krrz.KrpcVersion3.domain.RPCRequest;
import com.krrz.KrpcVersion3.domain.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class RPCClientProxy implements InvocationHandler {
    private RPCClient client;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request=RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .paramsTypes(method.getParameterTypes())
                .params(args).build();
        RPCResponse response= client.sendRequest(request);

        return response.getData();
    }

    <T>T getProxy(Class<T> clazz){
        Object o= Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
        return (T) o;
    }
}

package com.krrz.KrpcVersion3.server;

import com.krrz.KrpcVersion3.domain.RPCRequest;
import com.krrz.KrpcVersion3.domain.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/*
 *  这里负责解析得到的request请求，执行服务方法，返回给客户端
 * 1. 从request得到interfaceName 2. 根据interfaceName在serviceProvide Map中获取服务端的实现类
 * 3. 从request中得到方法名，参数， 利用反射执行服务中的方法 4. 得到结果，封装成response，写入socket
 */
@AllArgsConstructor
public class WorkThread implements Runnable{
    private Socket socket;
    private ServiceProvider serviceProvider;

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            //读取request
            RPCRequest request = (RPCRequest) objectInputStream.readObject();
            //封装返回
            RPCResponse response = getResponse(request);
            //写入到客户端
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据转换错误");
        }
    }
    private RPCResponse getResponse(RPCRequest request){
        //得到服务名
        String interfaceName=request.getInterfaceName();
        //得到服务端对应的实现类
        Object service = serviceProvider.getService(interfaceName);
        //反射调用方法
        Method method=null;
        try {
            method=service.getClass().getMethod(request.getMethodName(),request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException| InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return RPCResponse.fail();
        }
    }
}

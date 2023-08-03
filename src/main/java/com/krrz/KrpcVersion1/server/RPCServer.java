package com.krrz.KrpcVersion1.server;


import com.krrz.KrpcVersion1.domain.RPCRequest;
import com.krrz.KrpcVersion1.domain.RPCResponse;
import com.krrz.KrpcVersion1.service.UserService;
import com.krrz.KrpcVersion1.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    private static UserService userService=new UserServiceImpl();
    public static void main(String args[]){
        try {
            ServerSocket serverSocket=new ServerSocket(8899);
            System.out.println("服务器启动了");
            //以BIO的形式监听socket
            while(true){
                Socket socket=serverSocket.accept();
                //开启一个线程处理
                new Thread(()->{
                    try {
                        ObjectOutputStream oout=new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream oin=new ObjectInputStream(socket.getInputStream());
                        //读取客户端的request
                        RPCRequest o = (RPCRequest) oin.readObject();
                        //反射调用对应方法
                        Method method=userService.getClass().getMethod(o.getMethodName(),o.getParamsTypes());
                        Object invoke = method.invoke(userService, o.getParams());
                        //封装返回response
                        oout.writeObject(RPCResponse.success(invoke));
                        oout.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                        System.out.println("IO读取数据错误");
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}

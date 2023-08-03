package com.krrz.KrpcVersion0.server;

import com.krrz.KrpcVersion0.domain.User;
import com.krrz.KrpcVersion0.service.UserService;
import com.krrz.KrpcVersion0.service.impl.UserServiceImpl;
import io.netty.util.concurrent.BlockingOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class RPCServer {
    static Logger logger= LoggerFactory.getLogger(RPCServer.class);
    static UserService userService=new UserServiceImpl();
    public static void main(String args[]){
        try {
            ServerSocket serverSocket=new ServerSocket(8899);
            System.out.println("服务端启动");
            //用BIO的方式去监听Socket
            while (true){
                //serversocket.accept（）用法是一个阻塞方法 ，会一直等待直到获取一个客户端请求
                Socket socket=serverSocket.accept();
                new Thread(()->{
                    try {
                        ObjectOutputStream oout=new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream oin=new ObjectInputStream(socket.getInputStream());
                        //读取客户端传来的ID
                        int id = oin.readInt();
                        User user = userService.getUserById(id);
                        //返回给客户端
                        oout.writeObject(user);
                        oout.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.info("数据读取错误");
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("服务端启动失败");
        }

    }
}

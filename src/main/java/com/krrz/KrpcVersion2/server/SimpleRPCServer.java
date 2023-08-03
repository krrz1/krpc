package com.krrz.KrpcVersion2.server;

import org.apache.log4j.net.SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 这个实现类用java原始的BIO监听模式
 * 处理任务见workThread中
 */
public class SimpleRPCServer implements RPCServer{
    private ServiceProvider serviceProvider;

    public SimpleRPCServer(ServiceProvider serviceProvider){
        this.serviceProvider=serviceProvider;
    }
    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("服务端启动成功!");
            //用BIO方式监听Socket
            while(true){
                Socket socket=serverSocket.accept();
                new Thread(new WorkThread(socket,serviceProvider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务端启动失败");
        }

    }

    @Override
    public void stop() {

    }
}

package com.krrz.KrpcVersion3.server;

import com.krrz.KrpcVersion2.server.RPCServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 这个实现类代表着java原始的BIO监听模式，来一个任务，就new一个线程去处理
 * 处理任务的工作见WorkThread中
 */
public class SimpleRPCServer implements RPCServer {
    private ServiceProvider serviceProvider;

    public SimpleRPCServer(ServiceProvider serviceProvider){
        this.serviceProvider=serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("服务端启动了");
            //用BIO的方式监听Socket
            while(true){
                Socket socket=serverSocket.accept();
                //开启一个新线程
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

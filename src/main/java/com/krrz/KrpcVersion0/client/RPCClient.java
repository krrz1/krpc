package com.krrz.KrpcVersion0.client;

import com.krrz.KrpcVersion0.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class RPCClient {
    static Logger logger = LoggerFactory.getLogger(RPCClient.class);
    public static void main(String[] args){
        try {
            Socket socket=new Socket("127.0.0.1",8899);
            ObjectOutputStream oout=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream oin=new ObjectInputStream(socket.getInputStream());
            //传给服务端ID
            oout.writeInt(new Random().nextInt());
            oout.flush();
            //接收服务端传来的结果
            User user = (User)oin.readObject();
            System.out.println(user);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务端启动失败");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("服务端启动失败");
        }

    }
}

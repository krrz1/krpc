package com.krrz.KrpcVersion1.client;

import com.krrz.KrpcVersion1.domain.RPCRequest;
import com.krrz.KrpcVersion1.domain.RPCResponse;
import org.omg.PortableInterceptor.HOLDING;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class IOClient {
    // 这里负责底层与服务端的通信，发送的Request，接受的是Response对象
    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的（上层进行封装），不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    public static RPCResponse sendRequest(String host, int port, RPCRequest request){
        try {
            Socket socket=new Socket(host,port);

            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());

            System.out.println(request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            RPCResponse response= (RPCResponse) objectInputStream.readObject();

            return response;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

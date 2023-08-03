package com.krrz.KrpcVersion7.codec;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.krrz.KrpcVersion7.domain.Blog;
import com.krrz.KrpcVersion7.domain.RPCRequest;
import com.krrz.KrpcVersion7.domain.RPCResponse;
import com.krrz.KrpcVersion7.domain.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer implements Serializer{

    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RPCRequest.class);
        kryo.register(RPCResponse.class);
        kryo.register(java.lang.Object[].class);
        kryo.register(java.lang.Class[].class);
        kryo.register(java.lang.Class.class);
        kryo.register(User.class);
        kryo.register(Blog.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try{
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            Output output=new Output(byteArrayOutputStream);
            Kryo kryo=kryoThreadLocal.get();
            //object->byte:将对象序列化为byte数组
            kryo.writeObject(output,obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        }catch (Exception e){
            throw new RuntimeException("Serialization failed");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj=null;
        switch (messageType){
            case 0:
                try {
                    ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
                    Input input=new Input(byteArrayInputStream);
                    Kryo kryo=kryoThreadLocal.get();
                    //byte-》Object：从byte数组中反序列化出对象
                    Object o=kryo.readObject(input,RPCRequest.class);
                    kryoThreadLocal.remove();
                    obj=o;
                    break;
                }catch (Exception e){
                    throw new RuntimeException("Serialization failed");
                }
            case 1:
                try {
                    ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
                    Input input=new Input(byteArrayInputStream);
                    Kryo kryo=kryoThreadLocal.get();
                    //byte-》Object：从byte数组中反序列化出对象
                    Object o=kryo.readObject(input,RPCResponse.class);
                    kryoThreadLocal.remove();
                    obj=o;
                    break;
                }catch (Exception e){
                    throw new RuntimeException("Serialization failed");
                }
        }
        return obj;
    }

    @Override
    public int getType() {
        return 4;
    }
}

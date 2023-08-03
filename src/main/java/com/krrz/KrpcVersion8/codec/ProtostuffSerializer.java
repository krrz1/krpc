package com.krrz.KrpcVersion8.codec;

import com.krrz.KrpcVersion8.domain.RPCRequest;
import com.krrz.KrpcVersion8.domain.RPCResponse;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * google 的 protobuf序列化方式
 */
public class ProtostuffSerializer implements Serializer {

    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    //编码
    @Override
    public byte[] serialize(Object obj) {
        Class<?> clazz=obj.getClass();
        Schema schema= RuntimeSchema.getSchema(clazz);
        byte[] bytes=null;
        try{
            bytes = ProtostuffIOUtil.toByteArray(obj,schema,BUFFER);
        }finally {
            BUFFER.clear();
        }
        return bytes;
    }
    //解码
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj=null;
        switch (messageType){
            case 0:
                Schema<RPCRequest> schema=RuntimeSchema.getSchema(RPCRequest.class);
                RPCRequest request=schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes,request,schema);
                obj=request;
                break;
            case 1:
                Schema<RPCResponse> schema2=RuntimeSchema.getSchema(RPCResponse.class);
                RPCResponse response=schema2.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes,response,schema2);
                obj=response;
                break;
            default:
                System.out.println("暂不支持此类消息");
                throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 3;
    }
}

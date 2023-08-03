package com.krrz.KrpcVersion5.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
/*
 * 解码
 */
public class MyDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //1.读取消息的类型
        short messageType = in.readShort();
        //现在只支持request和response请求
        if(messageType != MessageType.REQUEST.getCode() && messageType!= MessageType.RESPONSE.getCode()){
            System.out.println("暂不支持此种数据类型");
            return;
        }
        //2. 读取序列化的类型
        short serializerType=in.readShort();
        //根据类型得到相应的序列化器
        Serializer serializer= Serializer.getSerializerByCode(serializerType);
        if(serializer==null){
            throw new RuntimeException("不存在相应的序列化器");
        }
        int length = in.readInt();
        byte[] bytes=new byte[length];
        in.readBytes(bytes);
        //用对应的序列化器解码字节数组
        Object deserialize = serializer.deserialize(bytes, messageType);
        out.add(deserialize);
    }
}

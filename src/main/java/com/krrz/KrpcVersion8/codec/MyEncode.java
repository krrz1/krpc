package com.krrz.KrpcVersion8.codec;

import com.krrz.KrpcVersion8.domain.RPCRequest;
import com.krrz.KrpcVersion8.domain.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/*
 *  编码  协议先 协议魔数-消息类型-序列化的方式-消息长度-消息内容
 */
@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;

    //协议魔数 4字节
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out) throws Exception {
        System.out.println(msg.getClass());
        //声明协议包，Magic Number魔数，表识一个 MRF 协议包，0xCAFEBABE
        out.writeInt(MAGIC_NUMBER);
        //写入消息类型
        if(msg instanceof RPCRequest){
            out.writeShort(MessageType.REQUEST.getCode());
        }else if(msg instanceof RPCResponse){
            out.writeShort(MessageType.RESPONSE.getCode());
        }
        //写入序列化的方式
        out.writeShort(serializer.getType());
        //得到序列化的数组
        byte[] serialize = serializer.serialize(msg);
        //写入长度
        out.writeInt(serialize.length);
        //写入序列号数组
        out.writeBytes(serialize);
    }
}

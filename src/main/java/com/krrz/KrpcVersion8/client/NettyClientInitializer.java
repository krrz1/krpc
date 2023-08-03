package com.krrz.KrpcVersion8.client;

import com.krrz.KrpcVersion8.codec.KryoSerializer;
import com.krrz.KrpcVersion8.codec.MyDecode;
import com.krrz.KrpcVersion8.codec.MyEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 同样的与服务端解码和编码格式
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        //用自定义的解码器
        pipeline.addLast(new MyDecode());
        //编码需要传入序列化器，这里是json,还支持ObjectSerializer，也可以实现其他的
        pipeline.addLast(new MyEncode(new KryoSerializer()));
        pipeline.addLast(new NettyClientHandler());
    }
}

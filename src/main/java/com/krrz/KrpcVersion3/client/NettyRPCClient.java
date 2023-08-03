package com.krrz.KrpcVersion3.client;

import com.krrz.KrpcVersion3.domain.RPCRequest;
import com.krrz.KrpcVersion3.domain.RPCResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.omg.CORBA.PRIVATE_MEMBER;

public class NettyRPCClient implements RPCClient{
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;
    private String host;
    private int port;
    public NettyRPCClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            ChannelFuture channelFuture=bootstrap.connect(host,port).sync();
            Channel channel=channelFuture.channel();
            //发送数据
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
            //阻塞获得的结果，通过给channel命名，获取特定名字下的channel的内容（这个再handle中设置）
            //attributekey是，线程隔离的，不会有线程安全问题
            //实际上不应通过阻塞，可通过回调函数
            AttributeKey<RPCResponse> key=AttributeKey.valueOf("RPCResponse");
            RPCResponse response=channel.attr(key).get();

            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    // netty客户端初始化，重复使用
    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }
}

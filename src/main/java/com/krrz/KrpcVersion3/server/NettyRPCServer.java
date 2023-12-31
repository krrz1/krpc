package com.krrz.KrpcVersion3.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyRPCServer implements RPCServer{
    private ServiceProvider serviceProvider;
    @Override
    public void start(int port) {
        //netty 服务线程组Boss负责建立连接，work负责处理请求
        NioEventLoopGroup boosGroup=new NioEventLoopGroup();
        NioEventLoopGroup workGroup=new NioEventLoopGroup();
        System.out.println("netty服务启动了");
        try {
            //启动netty服务器
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            //初始化
            serverBootstrap.group(boosGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceProvider));
            //同步阻塞
            ChannelFuture channelFuture=serverBootstrap.bind(port).sync();
            //死循环监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}

package com.krrz.KrpcVersion3.server;

import com.krrz.KrpcVersion3.domain.RPCRequest;
import com.krrz.KrpcVersion3.domain.RPCResponse;
import com.krrz.KrpcVersion3.server.ServiceProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    private ServiceProvider serviceProvider;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCRequest request) throws Exception {
        RPCResponse response=getResponse(request);
        channelHandlerContext.writeAndFlush(response);
        channelHandlerContext.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    RPCResponse getResponse(RPCRequest request){
        //得到服务名
        String interfaceName=request.getInterfaceName();
        //得到服务端对应的服务类
        Object service=serviceProvider.getService(interfaceName);
        //反射调用方法
        Method method=null;
        try{
            method=service.getClass().getMethod(request.getMethodName(),request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RPCResponse.fail();
        }
    }
}

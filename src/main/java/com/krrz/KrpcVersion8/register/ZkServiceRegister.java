package com.krrz.KrpcVersion8.register;

import com.krrz.KrpcVersion8.loadBalance.LoadBalance;
import com.krrz.KrpcVersion8.loadBalance.RandomLoadBalance;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZkServiceRegister implements ServiceRegister {
    //curator 提供的zookeeper客户端
    private CuratorFramework client;
    //zookeeper根路径节点
    private static final String ROOT_PATH="KRPC";
    private LoadBalance loadBalance=new RandomLoadBalance();

    private Map<String,List> serviceMapCache=new HashMap<>();
    //负责zookeeper客户端的初始化，并与zookeeper服务端建立链接
    public ZkServiceRegister(){
        //指数时间重试
        RetryPolicy policy=new ExponentialBackoffRetry(1000,3);
        //zookeeper地址固定,不管是服务提供者还是消费者都要与之建立链接
        //sessionTimeoutMS 与 zoo.cfg 的tickTime有关系
        //zk还会根据minSessionTimeout与MaxSessionTimeout两个参数调整最后的超时值。默认分别为tickTime的2倍和20倍
        //使用心跳监听状态
        this.client= CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zookeeper 连接成功");
    }
    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            //serviceName创建成永久节点，服务提供者下线时，不删服务名，只删地址
            if(client.checkExists().forPath("/"+serviceName)==null){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+serviceName);
            }
            //路径地址，一个/代表一个节点
            String path="/"+serviceName+"/"+getServiceAddress(serverAddress);
            //临时节点,服务器下线就删除节点
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            System.out.println("此服务已存在");
        }
    }
    //返回服务名地址
    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            //只有第一次请求提供服务的节点需要和zookeeper通讯，接下来存储在serviceMapCache中
            List<String> candidate=null;
            List cache = serviceMapCache.getOrDefault(serviceName, null);
            if(cache==null){
                candidate=client.getChildren().forPath("/"+serviceName);
                serviceMapCache.put(serviceName,candidate);
                System.out.println("client与zookeeper客户端通讯得到地址");
            }else{
                candidate=cache;
                System.out.println("clien从缓存得到地址");
            }

            //这里默认使用第一个，后面加负载均衡
            String string=loadBalance.balance(candidate);
            return parseAddress(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //地址-》 xxx.xxx.xxx.xxx:port 字符串
    private String getServiceAddress(InetSocketAddress serverAddress){
        return serverAddress.getHostName()+":"+serverAddress.getPort();
    }
    //字符串解析地址
    private InetSocketAddress parseAddress(String address){
        String[] result=address.split(":");
        return new InetSocketAddress(result[0],Integer.parseInt(result[1]));
    }
}

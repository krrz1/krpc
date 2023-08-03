package com.krrz.KrpcVersion7.loadBalance;

import java.util.List;

/**
 * 给服务器地址列表，根据不同负载均衡策略选一个
 */
public interface LoadBalance {
    String balance(List<String> addressList);
}

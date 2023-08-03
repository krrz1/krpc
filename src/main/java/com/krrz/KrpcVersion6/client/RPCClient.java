package com.krrz.KrpcVersion6.client;


import com.krrz.KrpcVersion6.domain.RPCRequest;
import com.krrz.KrpcVersion6.domain.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}

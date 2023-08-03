package com.krrz.KrpcVersion7.client;


import com.krrz.KrpcVersion7.domain.RPCRequest;
import com.krrz.KrpcVersion7.domain.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}

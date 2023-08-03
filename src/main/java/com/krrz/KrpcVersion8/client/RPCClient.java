package com.krrz.KrpcVersion8.client;


import com.krrz.KrpcVersion8.domain.RPCRequest;
import com.krrz.KrpcVersion8.domain.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}

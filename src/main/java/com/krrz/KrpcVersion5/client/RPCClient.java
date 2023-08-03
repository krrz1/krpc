package com.krrz.KrpcVersion5.client;


import com.krrz.KrpcVersion5.domain.RPCRequest;
import com.krrz.KrpcVersion5.domain.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}

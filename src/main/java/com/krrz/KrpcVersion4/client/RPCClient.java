package com.krrz.KrpcVersion4.client;


import com.krrz.KrpcVersion4.domain.RPCRequest;
import com.krrz.KrpcVersion4.domain.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}

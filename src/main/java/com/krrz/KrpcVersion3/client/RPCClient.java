package com.krrz.KrpcVersion3.client;

import com.krrz.KrpcVersion3.domain.RPCRequest;
import com.krrz.KrpcVersion3.domain.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}

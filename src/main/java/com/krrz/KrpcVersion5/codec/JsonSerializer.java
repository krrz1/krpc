package com.krrz.KrpcVersion5.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.krrz.KrpcVersion5.domain.RPCRequest;
import com.krrz.KrpcVersion5.domain.RPCResponse;

/**
 * 由于json序列化的方式是通过把对象转化成字符串，丢失了Data对象的类信息，所以deserialize需要
 * 了解对象对象的类信息，根据类信息把JsonObject -> 对应的对象
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes= JSONObject.toJSONBytes(obj);
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        //传输信息分为 requset 与 response
        switch (messageType) {
            case 0:
                RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
                Object[] objects = new Object[request.getParams().length];
                //把 json 字串传化成对应的对象，fastjson可以读出基本数据类型，不用转化
                for (int i = 0; i < objects.length; i++) {
                    Class<?> paramsType = request.getParamsTypes()[i];
                    if (!paramsType.isAssignableFrom(request.getParams()[i].getClass())) {
                        objects[i] = JSONObject.toJavaObject((JSONObject) request.getParams()[i], request.getParamsTypes()[i]);
                    } else {
                        objects[i] = request.getParams()[i];
                    }
                }
                request.setParams(objects);
                obj = request;
                break;
            case 1:
                RPCResponse response = JSON.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = response.getDataType();
                if (!dataType.isAssignableFrom(response.getData().getClass())) {
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(), dataType));
                }
                obj = response;
                break;
            default:
                System.out.println("暂不支持此类消息");
                throw new RuntimeException();
        }
        return obj;
    }

    //1 代表json序列化方式
    @Override
    public int getType() {
        return 1;
    }
}

package com.krrz.KrpcVersion5.codec;


public interface Serializer {
    //把对象序列化成数组
    byte[] serialize(Object obj);
    //从字节数组反序列化成消息，使用java自带序列方式不用messageType也能得到相应的对象（序列化字节数组里包含其他类的信息）
    //其他方式需要指定消息格式，再根据message转化成相应的对象
    Object deserialize(byte[] bytes,int messageType);
    //返回使用的序列器是哪个
    //O：java自带的序列化方式，1.json序列化方式
    int getType();
    //根据序号取出序列化器，暂时有两种实现方式，需要其他方式，实现这个接口即可
    static Serializer getSerializerByCode(int code){
        switch (code){
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}

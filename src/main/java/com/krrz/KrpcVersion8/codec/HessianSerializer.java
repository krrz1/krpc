package com.krrz.KrpcVersion8.codec;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        try{
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            HessianOutput hessianOutput=new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj=null;
        try{
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
            HessianInput hessianInput=new HessianInput(byteArrayInputStream);
            obj=hessianInput.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    @Override
    public int getType() {
        return 2;
    }
}

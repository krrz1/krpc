package com.krrz.KrpcVersion8.annotation;

import com.krrz.KrpcVersion8.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.beans.Customizer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Deprecated
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KrpcServer {
    String[] basePackage();
}

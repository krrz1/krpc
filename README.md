# krpc

一共有8个版本迭代，后续两个为作者写的，前面部分是这位大佬https://github.com/he2121/MyRPCFromZero



## RPC的概念

#### 背景知识

- RPC的基本概念，核心功能

![image-20200805001037799](http://ganghuan.oss-cn-shenzhen.aliyuncs.com/img/image-20200805124759206.png)

常见的RPC框架

#### Duboo基本功能

1. **远程通讯**
2. 基于接口方法的透明远程过程调用
3. 负载均衡
4. 服务注册中心

#### RPC过程

client 调用远程方法-> request序列化 -> 协议编码 -> 网络传输-> 服务端 -> 反序列化request -> 调用本地方法得到response -> 序列化 ->编码->…..



------



## 版本迭代过程

### 目录

从0开始的RPC的迭代过程：

- [version0版本](#0.一个最简单的RPC调用)：以不到百行的代码完成一个RPC例子
- [version1版本](#1.MyRPC版本1)：完善通用消息格式（request，response），客户端的动态代理完成对request消息格式的封装
- [version2版本](#2.MyRPC版本2)：支持服务端暴露多个服务接口， 服务端程序抽象化，规范化
- [version3版本](#3.MyRPC版本3)：使用高性能网络框架netty的实现网络通信，以及客户端代码的重构
- [version4版本](#4.MyRPC版本4)：自定义消息格式，支持多种序列化方式（java原生， json…）
- [version5版本](#5.MyRPC版本5):   服务器注册与发现的实现，zookeeper作为注册中心
- [version6版本](#MyRPC版本6):   负载均衡的策略的实现
- [version7版本](#7.MyRPC版本7):   客户端缓存服务地址列表, zookeeper监听服务提供者状态，更新客户端缓存，跨语言的RPC通信（protobuf）
- [version8版本](#8.MyRPC版本8)： 集成spring框架，将service用自定义的注解标注即可自动注册



### krpc版本7

dubbo的序列化使用的hessian，grpc使用的是protobuf，都支持跨语言

Kryo是一种非常成熟的序列化实现，已经在Twitter、Groupon、 Yahoo以及多个著名开源项目(如Hive、Storm)中广泛的使用，它的性能在各个方面都比hessian2要优秀些，因此dubbo后期也开始渐渐引入了使用Kryo进行序列化的方式。

google protobuf是一个灵活的、高效的用于序列化数据的协议。相比较XML和JSON格式，protobuf更小、更快、更便捷。google protobuf是跨语言的，并且自带了一个编译器(protoc)，只需要用它进行编译，可以编译成Java、python、C++、C#、Go等代码，然后就可以直接使用，不需要再写其他代码，自带有解析的代码。

protobuf相对于kryo来说具有更加高效的性能和灵活性，能够在实际使用中，当对象序列化之后新增了字段，在反序列化出来的时候依旧可以正常使用。(这一点kryo无法支持)

https://www.jianshu.com/p/937883b6b2e5

#### 总结

这一版本，新增了Kryo、protostuff、hessian序列化方法，新增了客户端的节点记录（host：port）缓存，不需要每次调用服务都去通讯zookeeper获得，只有第一次需要，接下来的通讯都从缓存中取。



### krpc版本8

#### 总结

继承了spring框架，将service的注册方法改成了自定义的注解，注解即可在服务端注册service
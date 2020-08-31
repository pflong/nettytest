## 前言

本项目用来记录 netty 的学习过程。


#### 运行命令
```bash
mvn compile


# 服务端
cd nettytest-server

mvn exec:java -Dexec.mainClass="com.iconyu.nettytest.server.EchoServer" -Dexec.args="9999"

## 客户端
cd nettytest-client

mvn exec:java -Dexec.mainClass="com.iconyu.nettytest.client.EchoClient" -Dexec.args="127.0.0.1 9999"
```


### heartbeat demo

> https://segmentfault.com/a/1190000006931568

ReconnectClient 是能够自动重连的 client

```bash
mvn compile

mvn exec:java -Dexec.mainClass="com.iconyu.nettytest.heartbeat.Server"
```

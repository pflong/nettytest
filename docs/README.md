
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

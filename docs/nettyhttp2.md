### Relevant Articles:
- [Introduction to Netty](https://www.baeldung.com/netty)
- [HTTP/2 in Netty](https://www.baeldung.com/netty-http2)
- [HTTP Server with Netty](https://www.baeldung.com/java-netty-http-server)

本模块下面的代码都是上面链接文章的。

注意，下面的依赖不可少

```xml
        <dependency>
            <groupId>org.conscrypt</groupId>
            <artifactId>conscrypt-openjdk-uber</artifactId>
            <version>2.4.0</version>
        </dependency>
```



### 坑一, jdk 版本不对
通过 jdk8 启动了 http2 server，通过例子中的 client 或者在虚机上通过 curl 访问都报错

#### 客户端（虚机）
```text
root@debian10:~# curl -k -v --http2  https://192.168.33.1:8443
* Expire in 0 ms for 6 (transfer 0x55f78fa78f50)
*   Trying 192.168.33.1...
* TCP_NODELAY set
* Expire in 200 ms for 4 (transfer 0x55f78fa78f50)
* Connected to 192.168.33.1 (192.168.33.1) port 8443 (#0)
* ALPN, offering h2
* ALPN, offering http/1.1
* successfully set certificate verify locations:
*   CAfile: none
  CApath: /etc/ssl/certs
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* OpenSSL SSL_connect: SSL_ERROR_SYSCALL in connection to 192.168.33.1:8443
* Closing connection 0
curl: (35) OpenSSL SSL_connect: SSL_ERROR_SYSCALL in connection to 192.168.33.1:8443
```

#### 服务端
```text

/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=55148:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Users/long/pflong/nettytest/nettyhttp2/target/classes:/Users/long/.m2/repository/org/conscrypt/conscrypt-openjdk-uber/2.4.0/conscrypt-openjdk-uber-2.4.0.jar:/Users/long/.m2/repository/io/netty/netty-all/4.1.48.Final/netty-all-4.1.48.Final.jar:/Users/long/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/long/.m2/repository/org/slf4j/slf4j-log4j12/1.7.25/slf4j-log4j12-1.7.25.jar:/Users/long/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar com.iconyu.nettyhttp2.http2.server.Http2Server
[AppLog] 2020-08-27 22:24:02   [id: 0x5efbd293] REGISTERED 
[AppLog] 2020-08-27 22:24:02   [id: 0x5efbd293] BIND: 0.0.0.0/0.0.0.0:8443 
[AppLog] 2020-08-27 22:24:02   HTTP/2 Server is listening on https://127.0.0.1:8443/ 
[AppLog] 2020-08-27 22:24:02   [id: 0x5efbd293, L:/0:0:0:0:0:0:0:0:8443] ACTIVE 
[AppLog] 2020-08-27 22:24:10   [id: 0x5efbd293, L:/0:0:0:0:0:0:0:0:8443] READ: [id: 0x3cf9a9c2, L:/192.168.33.1:8443 - R:/192.168.33.181:36846] 
[AppLog] 2020-08-27 22:24:10   [id: 0x5efbd293, L:/0:0:0:0:0:0:0:0:8443] READ COMPLETE 
[AppLog] 2020-08-27 22:24:10   Failed to initialize a channel. Closing: [id: 0x3cf9a9c2, L:/192.168.33.1:8443 - R:/192.168.33.181:36846] 
java.lang.RuntimeException: Unable to wrap SSLEngine of type sun.security.ssl.SSLEngineImpl
	at io.netty.handler.ssl.JdkAlpnApplicationProtocolNegotiator$AlpnWrapper.wrapSslEngine(JdkAlpnApplicationProtocolNegotiator.java:144)
	at io.netty.handler.ssl.JdkSslContext.configureAndWrapEngine(JdkSslContext.java:360)
	at io.netty.handler.ssl.JdkSslContext.newEngine(JdkSslContext.java:330)
	at io.netty.handler.ssl.SslContext.newHandler(SslContext.java:953)
	at io.netty.handler.ssl.SslContext.newHandler(SslContext.java:945)
	at com.iconyu.nettyhttp2.http2.server.Http2Server$1.initChannel(Http2Server.java:43)
	at com.iconyu.nettyhttp2.http2.server.Http2Server$1.initChannel(Http2Server.java:38)
	at io.netty.channel.ChannelInitializer.initChannel(ChannelInitializer.java:129)
	at io.netty.channel.ChannelInitializer.handlerAdded(ChannelInitializer.java:112)
	at io.netty.channel.AbstractChannelHandlerContext.callHandlerAdded(AbstractChannelHandlerContext.java:971)
	at io.netty.channel.DefaultChannelPipeline.callHandlerAdded0(DefaultChannelPipeline.java:609)
	at io.netty.channel.DefaultChannelPipeline.access$100(DefaultChannelPipeline.java:46)
	at io.netty.channel.DefaultChannelPipeline$PendingHandlerAddedTask.execute(DefaultChannelPipeline.java:1463)
	at io.netty.channel.DefaultChannelPipeline.callHandlerAddedForAllHandlers(DefaultChannelPipeline.java:1115)
	at io.netty.channel.DefaultChannelPipeline.invokeHandlerAddedIfNeeded(DefaultChannelPipeline.java:650)
	at io.netty.channel.AbstractChannel$AbstractUnsafe.register0(AbstractChannel.java:502)
	at io.netty.channel.AbstractChannel$AbstractUnsafe.access$200(AbstractChannel.java:417)
	at io.netty.channel.AbstractChannel$AbstractUnsafe$1.run(AbstractChannel.java:474)
	at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:164)
	at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:472)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:500)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.run(Thread.java:748)
```


### 坑二

多个版本的 jdk 采用下面的方式进行管理
```text
export JAVA_8_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home"
export JAVA_11_HOME="/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home"
export JAVA_HOME=$JAVA_8_HOME
alias jdk8="export JAVA_HOME=$JAVA_8_HOME"
alias jdk11="export JAVA_HOME=$JAVA_11_HOME"
```

由于 idea 没有识别 java11，所以我们通过命令行运行
```text
➜  nettyhttp2 git:(master) ✗ pwd
/Users/long/pflong/nettytest/nettyhttp2

➜  nettyhttp2 git:(master) ✗ mvn exec:java -Dexec.mainClass="com.iconyu.nettyhttp2.http2.server.Http2Server"
```

在客户端必须要采用 https 访问，否则会出现下面的报错

客户端
```text
root@debian10:~# curl -k -v --http2  http://192.168.33.1:8443
* Expire in 0 ms for 6 (transfer 0x55e7abb97f50)
*   Trying 192.168.33.1...
* TCP_NODELAY set
* Expire in 200 ms for 4 (transfer 0x55e7abb97f50)
* Connected to 192.168.33.1 (192.168.33.1) port 8443 (#0)
> GET / HTTP/1.1
> Host: 192.168.33.1:8443
> User-Agent: curl/7.64.0
> Accept: */*
> Connection: Upgrade, HTTP2-Settings
> Upgrade: h2c
> HTTP2-Settings: AAMAAABkAARAAAAAAAIAAAAA
>
* Empty reply from server
* Connection #0 to host 192.168.33.1 left intact
curl: (52) Empty reply from server
```

服务端
```text
[AppLog] 2020-08-27 22:31:48   [id: 0xa8e6605e, L:/0:0:0:0:0:0:0:0:8443] READ: [id: 0x9ed361dd, L:/192.168.33.1:8443 - R:/192.168.33.181:36848]
[AppLog] 2020-08-27 22:31:48   [id: 0xa8e6605e, L:/0:0:0:0:0:0:0:0:8443] READ COMPLETE
[AppLog] 2020-08-27 22:31:48   [id: 0x9ed361dd, L:/192.168.33.1:8443 - R:/192.168.33.181:36848] TLS handshake failed:
io.netty.handler.ssl.NotSslRecordException: not an SSL/TLS record: 474554202f20485454502f312e310d0a486f73743a203139322e3136382e33332e313a383434330d0a557365722d4167656e743a206375726c2f372e36342e300d0a4163636570743a202a2f2a0d0a436f6e6e656374696f6e3a20557067726164652c2048545450322d53657474696e67730d0a557067726164653a206832630d0a48545450322d53657474696e67733a2041414d414141426b414152414141414141414941414141410d0a0d0a
```

最终采用正确的 https 进行通信

客户端
```text
root@debian10:~# curl -k -v --http2  https://192.168.33.1:8443
* Expire in 0 ms for 6 (transfer 0x55888b086f50)
*   Trying 192.168.33.1...
* TCP_NODELAY set
* Expire in 200 ms for 4 (transfer 0x55888b086f50)
* Connected to 192.168.33.1 (192.168.33.1) port 8443 (#0)
* ALPN, offering h2
* ALPN, offering http/1.1
* successfully set certificate verify locations:
*   CAfile: none
  CApath: /etc/ssl/certs
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.2 (IN), TLS handshake, Certificate (11):
* TLSv1.2 (IN), TLS handshake, Server key exchange (12):
* TLSv1.2 (IN), TLS handshake, Server finished (14):
* TLSv1.2 (OUT), TLS handshake, Client key exchange (16):
* TLSv1.2 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.2 (OUT), TLS handshake, Finished (20):
* TLSv1.2 (IN), TLS handshake, Finished (20):
* SSL connection using TLSv1.2 / ECDHE-RSA-AES256-GCM-SHA384
* ALPN, server accepted to use h2
* Server certificate:
*  subject: CN=example.com
*  start date: Aug 28 14:31:41 2019 GMT
*  expire date: Dec 31 23:59:59 9999 GMT
*  issuer: CN=example.com
*  SSL certificate verify result: self signed certificate (18), continuing anyway.
* Using HTTP2, server supports multi-use
* Connection state changed (HTTP/2 confirmed)
* Copying HTTP/2 data in stream buffer to connection buffer after upgrade: len=0
* Using Stream ID: 1 (easy handle 0x55888b086f50)
> GET / HTTP/2
> Host: 192.168.33.1:8443
> User-Agent: curl/7.64.0
> Accept: */*
>
* Connection state changed (MAX_CONCURRENT_STREAMS == 4294967295)!
< HTTP/2 200
<
* Connection #0 to host 192.168.33.1 left intact
Hello World
```

服务端

```text
[AppLog] 2020-08-27 22:33:30   [id: 0xa8e6605e, L:/0:0:0:0:0:0:0:0:8443] READ: [id: 0xd2d3ca31, L:/192.168.33.1:8443 - R:/192.168.33.181:36852]
[AppLog] 2020-08-27 22:33:30   [id: 0xa8e6605e, L:/0:0:0:0:0:0:0:0:8443] READ COMPLETE
```


### 坑三（待解决）
解决了上面的问题之后，过一会服务端出现下面的异常


```text
[AppLog] 2020-08-27 22:36:41   [id: 0x5ae97441, L:/127.0.0.1:8443 - R:/127.0.0.1:55216] TLS handshake failed:
javax.net.ssl.SSLHandshakeException: Received fatal alert: certificate_unknown
	at java.base/sun.security.ssl.Alert.createSSLException(Alert.java:131)
	at java.base/sun.security.ssl.Alert.createSSLException(Alert.java:117)
	at java.base/sun.security.ssl.TransportContext.fatal(TransportContext.java:313)
	at java.base/sun.security.ssl.Alert$AlertConsumer.consume(Alert.java:293)
	at java.base/sun.security.ssl.TransportContext.dispatch(TransportContext.java:186)
	at java.base/sun.security.ssl.SSLTransport.decode(SSLTransport.java:171)
	at java.base/sun.security.ssl.SSLEngineImpl.decode(SSLEngineImpl.java:681)
	at java.base/sun.security.ssl.SSLEngineImpl.readRecord(SSLEngineImpl.java:636)
	at java.base/sun.security.ssl.SSLEngineImpl.unwrap(SSLEngineImpl.java:454)
	at java.base/sun.security.ssl.SSLEngineImpl.unwrap(SSLEngineImpl.java:433)
	at java.base/javax.net.ssl.SSLEngine.unwrap(SSLEngine.java:637)
	at io.netty.handler.ssl.JdkSslEngine.unwrap(JdkSslEngine.java:92)
	at io.netty.handler.ssl.Java9SslEngine.unwrap(Java9SslEngine.java:141)
	at io.netty.handler.ssl.SslHandler$SslEngineType$3.unwrap(SslHandler.java:281)
	at io.netty.handler.ssl.SslHandler.unwrap(SslHandler.java:1340)
	at io.netty.handler.ssl.SslHandler.decodeJdkCompatible(SslHandler.java:1235)
	at io.netty.handler.ssl.SslHandler.decode(SslHandler.java:1282)
	at io.netty.handler.codec.ByteToMessageDecoder.decodeRemovalReentryProtection(ByteToMessageDecoder.java:498)
	at io.netty.handler.codec.ByteToMessageDecoder.callDecode(ByteToMessageDecoder.java:437)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:276)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:357)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1410)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:919)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:714)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:650)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:576)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:834)
[AppLog] 2020-08-27 22:36:41   An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.
io.netty.handler.codec.DecoderException: javax.net.ssl.SSLHandshakeException: Received fatal alert: certificate_unknown
	at io.netty.handler.codec.ByteToMessageDecoder.callDecode(ByteToMessageDecoder.java:468)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:276)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:357)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1410)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:919)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:714)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:650)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:576)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: javax.net.ssl.SSLHandshakeException: Received fatal alert: certificate_unknown
	at java.base/sun.security.ssl.Alert.createSSLException(Alert.java:131)
	at java.base/sun.security.ssl.Alert.createSSLException(Alert.java:117)
	at java.base/sun.security.ssl.TransportContext.fatal(TransportContext.java:313)
	at java.base/sun.security.ssl.Alert$AlertConsumer.consume(Alert.java:293)
	at java.base/sun.security.ssl.TransportContext.dispatch(TransportContext.java:186)
	at java.base/sun.security.ssl.SSLTransport.decode(SSLTransport.java:171)
	at java.base/sun.security.ssl.SSLEngineImpl.decode(SSLEngineImpl.java:681)
	at java.base/sun.security.ssl.SSLEngineImpl.readRecord(SSLEngineImpl.java:636)
	at java.base/sun.security.ssl.SSLEngineImpl.unwrap(SSLEngineImpl.java:454)
	at java.base/sun.security.ssl.SSLEngineImpl.unwrap(SSLEngineImpl.java:433)
	at java.base/javax.net.ssl.SSLEngine.unwrap(SSLEngine.java:637)
	at io.netty.handler.ssl.JdkSslEngine.unwrap(JdkSslEngine.java:92)
	at io.netty.handler.ssl.Java9SslEngine.unwrap(Java9SslEngine.java:141)
	at io.netty.handler.ssl.SslHandler$SslEngineType$3.unwrap(SslHandler.java:281)
	at io.netty.handler.ssl.SslHandler.unwrap(SslHandler.java:1340)
	at io.netty.handler.ssl.SslHandler.decodeJdkCompatible(SslHandler.java:1235)
	at io.netty.handler.ssl.SslHandler.decode(SslHandler.java:1282)
	at io.netty.handler.codec.ByteToMessageDecoder.decodeRemovalReentryProtection(ByteToMessageDecoder.java:498)
	at io.netty.handler.codec.ByteToMessageDecoder.callDecode(ByteToMessageDecoder.java:437)
	... 17 more

```
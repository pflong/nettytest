package com.iconyu.nettyhttp2.http2.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http2.DefaultHttp2DataFrame;
import io.netty.handler.codec.http2.DefaultHttp2Headers;
import io.netty.handler.codec.http2.DefaultHttp2HeadersFrame;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersFrame;
import io.netty.util.CharsetUtil;

/**
 * This handler is, in turn, adding a Netty provided Http2FrameCodec using its builder and a custom
 * handler called Http2ServerResponseHandler.
 *
 * Our custom handler extends Netty's ChannelDuplexHandler and acts as both an inbound as well as
 * an outbound handler for the server. Primarily, it prepares the response to be sent to the client
 */
@Sharable
public class Http2ServerResponseHandler extends ChannelDuplexHandler {

    /**
     * we'll define a static Hello World response in an io.netty.buffer.ByteBuf – the preferred
     * object to read and write bytes in Netty:
     *
     * This buffer will be set as a DATA frame in our handler's channelRead method and written to the ChannelHandlerContext:
     */
    static final ByteBuf RESPONSE_BYTES = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8));

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Http2HeadersFrame) {
            Http2HeadersFrame msgHeader = (Http2HeadersFrame) msg;
            if (msgHeader.isEndStream()) {
                ByteBuf content = ctx.alloc()
                    .buffer();
                content.writeBytes(RESPONSE_BYTES.duplicate());

                Http2Headers headers = new DefaultHttp2Headers().status(HttpResponseStatus.OK.codeAsText());
                ctx.write(new DefaultHttp2HeadersFrame(headers).stream(msgHeader.stream()));
                ctx.write(new DefaultHttp2DataFrame(content, true).stream(msgHeader.stream()));
            }

        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}

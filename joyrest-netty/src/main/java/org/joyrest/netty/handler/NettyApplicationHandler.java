package org.joyrest.netty.handler;

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.joyrest.context.ApplicationContext;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpRequest;

public class NettyApplicationHandler extends SimpleChannelInboundHandler<HttpRequest> {

	/* Class for processing an incoming model and generated response */
	private final RequestProcessor processor;

	public NettyApplicationHandler(ApplicationContext applicationContext) {
		this(new RequestProcessorImpl(applicationContext));
	}

	public NettyApplicationHandler(RequestProcessor processor) {
		this.processor = processor;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext context, HttpRequest request) throws Exception {
//		if (HttpHeaderUtil.is100ContinueExpected(request))
//			send100Continue(context);

	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
		ctx.write(response);
	}
}
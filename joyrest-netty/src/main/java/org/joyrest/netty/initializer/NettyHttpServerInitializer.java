package org.joyrest.netty.initializer;

import org.joyrest.context.ApplicationContext;
import org.joyrest.netty.handler.NettyApplicationHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

	private final SslContext sslCtx;

	private final ApplicationContext context;

	public NettyHttpServerInitializer(ApplicationContext context) {
		this(context, null);
	}
	public NettyHttpServerInitializer(ApplicationContext context, SslContext sslCtx) {
		this.context = context;
		this.sslCtx = sslCtx;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if (nonNull(sslCtx))
			p.addLast(sslCtx.newHandler(ch.alloc()));

		p.addLast(new HttpRequestDecoder());
		// Uncomment the following line if you don't want to handle HttpChunks.
		// p.addLast(new HttpObjectAggregator(1048576));
		p.addLast(new HttpResponseEncoder());
		// Remove the following line if you don't want automatic content compression.
		// p.addLast(new HttpContentCompressor());
		p.addLast(new NettyApplicationHandler(context));
	}
}
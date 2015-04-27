package org.joyrest.netty;

import org.joyrest.context.ApplicationContext;
import org.joyrest.netty.initializer.NettyHttpServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public final class NettyServer {

	public static void start(final ApplicationContext context, final int port) throws Exception {
		start(context, port, false);
	}

	public static void start(final ApplicationContext context, final int port, final boolean ssl) throws Exception {
		SslContext sslCtx = null;
		if (ssl) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
		}

		try (NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
				NioEventLoopGroup workerGroup = new NioEventLoopGroup()) {

			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new NettyHttpServerInitializer(context, sslCtx));

			Channel ch = bootstrap.bind(port).sync().channel();

			System.err.println("Netty Server started. Stop the application using ^C. " +
					(ssl ? "https" : "http") + "localhost:" + port + '/');

			ch.closeFuture().sync();
		}
	}
}
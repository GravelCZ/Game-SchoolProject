package cz.vesely.game.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.vesely.game.common.network.NetworkHandler;
import cz.vesely.game.common.network.PacketCodec;
import cz.vesely.game.common.network.PacketSizer;
import cz.vesely.game.server.ServerGameLogic;
import cz.vesely.game.server.network.handler.login.HandlerServerLogic;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NetworkSystem {

	private static final NioEventLoopGroup GROUP = new NioEventLoopGroup(0);
	
	private ChannelFuture server;
	
	private final List<NetworkHandler> clients = Collections.synchronizedList(new ArrayList<>());
	
	private final ServerGameLogic logic;
	
	public NetworkSystem(ServerGameLogic logic) 
	{
		this.logic = logic;
	}
	
	public void createServerEndpoint(InetSocketAddress address) throws IOException, InterruptedException
	{
		if (server != null) {
			return;
		}
		server = new ServerBootstrap().channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<Channel>() {
			
			protected void initChannel(Channel ch) throws Exception {
				try {
					ch.config().setOption(ChannelOption.TCP_NODELAY, true);
				} catch (ChannelException e) {}
					
				NetworkHandler handler = new NetworkHandler();
				NetworkSystem.this.clients.add(handler);
				
				handler.setPacketListener(new HandlerServerLogic(logic));
				
				ch.pipeline()
					.addLast("timeout", new ReadTimeoutHandler(30))
					.addLast("framer", new PacketSizer())
					.addLast("codec", new PacketCodec())
					.addLast("handler", handler);
				
			};
		}).group(GROUP).localAddress(address).bind().awaitUninterruptibly();
	}
	
	public static NetworkHandler openChannel(InetSocketAddress address)
	{
		NetworkHandler handler = new NetworkHandler();
		
		Bootstrap b = new Bootstrap();
		b.group(GROUP).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
			
			@Override
			protected void initChannel(Channel ch) throws Exception 
			{
				try {
					ch.config().setOption(ChannelOption.TCP_NODELAY, true);
				} catch (ChannelException e) {}
				
				ch.pipeline()
					.addFirst(new LoggingHandler(LogLevel.DEBUG))
					.addLast("timeout", new ReadTimeoutHandler(30))
					.addLast("framer", new PacketSizer())
					.addLast("codec", new PacketCodec())
					.addLast("handler", handler);
			}
		}).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
		
		try {
			ChannelFuture f = b.connect(address).syncUninterruptibly();
		} catch (Throwable e) {
			handler.exceptionCaught(null, e);
		}
		return handler;
	}
	
	public void terminateEndpoint()
	{
		try {
			server.channel().close().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

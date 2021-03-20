package cz.vesely.game.client.network;

import cz.vesely.game.common.network.PacketSizer;
import cz.vesely.game.common.network.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientSession {
	
	private EventLoopGroup group;
	
	public ClientSession(String address, int port) 
	{
		Protocol.init();
	}
	
	public void connect()
	{
		try {
			this.group = new NioEventLoopGroup();

			final Bootstrap bootstrap = new Bootstrap();
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<Channel>() {
				
				protected void initChannel(Channel ch) throws Exception {
					
					ch.config().setOption(ChannelOption.IP_TOS, 0x18);
					ch.config().setOption(ChannelOption.TCP_NODELAY, true);
					
					ChannelPipeline pipeline = ch.pipeline();
					
					pipeline.addLast("sizer", new PacketSizer());
					pipeline.addLast("codec", new PacketCodec());
				}
				
			}).group(this.group).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}

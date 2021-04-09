package cz.vesely.game.common.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.TimeoutException;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

	private Channel channel;

	private INetHandler packetListener;

	private boolean disconnected;

	private String closeMessage;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);

		this.channel = ctx.channel();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		if (this.channel.isOpen()) {
			System.out.println("Recieved packet: " + msg.getClass().getName());
			msg.processPacket(packetListener);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof TimeoutException) {
			this.closeMessage = "Timed out";
		} else {
			this.closeMessage = "Internal exception: " + cause.getMessage();
		}
		
		this.closeChannel();
	}

	public void closeChannel() {
		if (this.channel != null && this.channel.isOpen()) {
			this.channel.close().awaitUninterruptibly();
		}
	}

	public void sendPacket(Packet packetIn) {
		if (this.channel != null && this.channel.isOpen()) {
			this.channel.writeAndFlush(packetIn).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		}
	}

	public void checkDisconnected()
	{
		if (this.channel != null && !this.channel.isOpen())
		{
			if (!this.disconnected) {
				this.disconnected = true;
				
				this.packetListener.onDisconnect(getCloseMessage());
			}
		}
	}
	
	public void setPacketListener(INetHandler packetListener) {
		this.packetListener = packetListener;
	}
	
	public void processPackets() {
		this.channel.flush();
	}

	public String getCloseMessage() {
		return closeMessage;
	}

}

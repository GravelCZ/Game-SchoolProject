package cz.vesely.game.common.network;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.TimeoutException;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

	private Channel channel;

	private INetHandler packetListener;

	private boolean disconnected = false;
	private String closeReason;

	private Queue<Packet> packetsIn = new ConcurrentLinkedQueue<>();
	private Queue<Packet> packetsOut = new ConcurrentLinkedQueue<>();
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.channel = ctx.channel();
		System.out.println("Connected");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (ctx.channel() == this.channel) {
			disconnect("Disconnected");
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		if (this.channel.isOpen()) {
			System.out.println("Recieved packet: " + msg.getClass().getName());
			packetsIn.add(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		String closeMessage;
		if (cause instanceof TimeoutException) {
			closeMessage = "Timed out";
		} else {
			closeMessage = "Internal exception: " + cause.getMessage();
		}

		disconnect(closeMessage);
	}

	public void networkTick() {
		if (this.disconnected) {
			this.packetListener.onDisconnect(closeReason);
			return;
		}
		if (this.channel != null && this.channel.isOpen()) {
			Packet packet;
			while ((packet = packetsIn.poll()) != null) {
				packet.processPacket(packetListener);
			}
			while ((packet = packetsOut.poll()) != null) {
				this.channel.writeAndFlush(packet).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
				System.out.println("Sent queued packet: " + packet.getClass().getSimpleName());
			}
		}
	}

	public void sendPacket(Packet packetIn) {
		if (this.channel != null) {
			if (this.channel.isOpen()) {
				this.channel.writeAndFlush(packetIn).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
				System.out.println("Sent packet: " + packetIn.getClass().getSimpleName());	
			} else {
				this.packetsOut.add(packetIn);
			}
		}
	}

	public void disconnect(String reason) {
		if (this.disconnected) {
			return;
		}
		
		this.disconnected = true;
		if (this.channel != null && this.channel.isOpen()) {
			this.channel.flush().close();
		}
		
		this.closeReason = reason;
	}

	public void setPacketListener(INetHandler packetListener) {
		this.packetListener = packetListener;
	}

}

package cz.vesely.game.common.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import cz.vesely.game.common.network.client.PacketClientLogin;
import cz.vesely.game.common.network.server.PacketServerDisconnect;

public class NetworkHandler extends Listener {

	private INetHandler packetListener;

	private boolean disconnected = false;
	private String closeReason;

	private Connection connection;
	
	@Override
	public void connected(Connection connection) 
	{
		this.connection = connection;
	}
	
	@Override
	public void disconnected(Connection connection) {
		
	}
	
	@Override
	public void received(Connection connection, Object object) {
		
	}
	
	/*public void networkTick() {
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
	}*/

	public void setPacketListener(INetHandler packetListener) {
		this.packetListener = packetListener;
	}

	public void sendPacket(Packet packet) 
	{
		connection.sendTCP(packet);
	}

	public void disconnect(String string) 
	{
		connection.sendTCP(new PacketServerDisconnect(string));
		connection.close();
	}

}

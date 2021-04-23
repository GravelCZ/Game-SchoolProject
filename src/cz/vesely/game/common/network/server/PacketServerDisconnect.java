package cz.vesely.game.common.network.server;

import java.io.IOException;

import cz.vesely.game.common.network.INetHandler;
import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
import cz.vesely.game.common.network.Packet;

public class PacketServerDisconnect implements Packet<INetHandler>  {

	private String reason;
	
	public PacketServerDisconnect() {}
	
	public PacketServerDisconnect(String reason) {
		this.reason = reason;
	}
	
	@Override
	public void read(NetInput in) throws IOException {
		this.reason = in.readString();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeString(reason);
	}

	@Override
	public void processPacket(INetHandler handler) {
		handler.onDisconnect(getReason());
	}

	public String getReason() {
		return reason;
	}
}

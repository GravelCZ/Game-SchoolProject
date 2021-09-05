package cz.vesely.game.common.network.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cz.vesely.game.common.network.INetHandler;
import cz.vesely.game.common.network.Packet;

public class PacketServerDisconnect implements Packet<INetHandler>  {

	private String reason;
	
	public PacketServerDisconnect() {}
	
	public PacketServerDisconnect(String reason) {
		this.reason = reason;
	}

	@Override
	public void read(Kryo kryo, Input in) {
		this.reason = in.readString();
	}
	
	@Override
	public void write(Kryo var1, Output var2) {
		
	}
	
	@Override
	public void processPacket(INetHandler handler) {
		handler.onDisconnect(getReason());
	}

	public String getReason() {
		return reason;
	}
}

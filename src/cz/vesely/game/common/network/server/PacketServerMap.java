package cz.vesely.game.common.network.server;

import java.io.IOException;

import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerMap implements Packet<INetHandlerClientLogin> {

	private String name;

	public PacketServerMap() {
	}

	public PacketServerMap(String name) {
		this.name = name;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.name = in.readString();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeString(name);
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleMap(this);
	}

}

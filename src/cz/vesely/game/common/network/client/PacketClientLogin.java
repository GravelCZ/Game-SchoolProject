package cz.vesely.game.common.network.client;

import java.io.IOException;

import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerServerLogin;

public class PacketClientLogin implements Packet<INetHandlerServerLogin> {

	private String name;
	private int version;

	public PacketClientLogin() {
	}

	public PacketClientLogin(String name, int version) {
		this.name = name;
		this.version = version;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.name = in.readString();
		this.version = in.readInt();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeString(name);
		out.writeInt(version);
	}

	@Override
	public void processPacket(INetHandlerServerLogin handler) {
		handler.handleLogin(this);
	}

	public String getName() {
		return name;
	}

	public int getVersion() {
		return version;
	}
}

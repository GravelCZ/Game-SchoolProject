package cz.vesely.game.common.network.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

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
	public void write(Kryo kryo, Output output) {
		output.writeString(name);
		output.writeInt(version);
	}

	@Override
	public void read(Kryo kryo, Input input) 
	{
		this.name = input.readString();
		this.version = input.readInt();
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

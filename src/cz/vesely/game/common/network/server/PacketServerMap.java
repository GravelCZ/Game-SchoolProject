package cz.vesely.game.common.network.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

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
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleMap(this);
	}

	@Override
	public void write(Kryo kryo, Output output) {
		output.writeString(name);
	}

	@Override
	public void read(Kryo kryo, Input input) {
		this.name = input.readString();
	}

}

package cz.vesely.game.common.network.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerLoginResponse implements Packet<INetHandlerClientLogin> {

	public boolean accepted;

	public PacketServerLoginResponse() {
	}

	public PacketServerLoginResponse(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleLoginResponse(this);
	}

	public boolean isAccepted() {
		return accepted;
	}

	@Override
	public void write(Kryo kryo, Output output) 
	{
		output.writeBoolean(accepted);
	}

	@Override
	public void read(Kryo kryo, Input input) 
	{
		this.accepted = input.readBoolean();
	}

}

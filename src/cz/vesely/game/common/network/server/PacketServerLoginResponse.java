package cz.vesely.game.common.network.server;

import java.io.IOException;

import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
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
	public void read(NetInput in) throws IOException {
		this.accepted = in.readBoolean();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeBoolean(accepted);
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleLoginResponse(this);
	}

	public boolean isAccepted() {
		return accepted;
	}

}

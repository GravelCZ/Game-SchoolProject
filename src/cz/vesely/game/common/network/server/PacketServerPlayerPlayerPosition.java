package cz.vesely.game.common.network.server;

import java.io.IOException;

import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerPlayerPlayerPosition implements Packet<INetHandlerClientLogin> {

	private float x, y;

	public PacketServerPlayerPlayerPosition() {
	}

	public PacketServerPlayerPlayerPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.x = in.readFloat();
		this.y = in.readFloat();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeFloat(x);
		out.writeFloat(y);
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleLoginPosition(this);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}

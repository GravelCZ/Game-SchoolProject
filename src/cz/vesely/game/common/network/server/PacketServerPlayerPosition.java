package cz.vesely.game.common.network.server;

import java.io.IOException;

import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerPlayerPosition implements Packet<INetHandlerClientLogin> {

	private float x, y;
	private int id;
	
	public PacketServerPlayerPosition() {
	}

	public PacketServerPlayerPosition(int id, float x, float y) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.id = in.readInt();
		this.x = in.readFloat();
		this.y = in.readFloat();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeInt(id);
		out.writeFloat(x);
		out.writeFloat(y);
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleLoginPosition(this);
	}

	public int getId() {
		return id;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}

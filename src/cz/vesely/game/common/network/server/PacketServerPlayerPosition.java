package cz.vesely.game.common.network.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

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
	public void read(Kryo kryo, Input in) {
		this.x = in.readFloat();
		this.y = in.readFloat();
	}
	
	@Override
	public void write(Kryo kryo, Output out) {
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

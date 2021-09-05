package cz.vesely.game.common.network.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cz.vesely.game.common.network.MapState;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerServerLogin;

public class PacketClientMapResponse implements Packet<INetHandlerServerLogin> {

	private MapState mapState;

	public PacketClientMapResponse() {
	}

	public PacketClientMapResponse(MapState state) {
		this.mapState = state;
	}

	@Override
	public void processPacket(INetHandlerServerLogin handler) {
		handler.handleMapResponse(handler);
	}

	public MapState getMapState() {
		return mapState;
	}

	@Override
	public void write(Kryo kryo, Output output) 
	{
		output.writeInt(this.mapState.ordinal());
	}

	@Override
	public void read(Kryo kryo, Input input) 
	{
		this.mapState = MapState.values()[input.readInt()];
	}

}

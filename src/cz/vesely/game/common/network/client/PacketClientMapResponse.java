package cz.vesely.game.common.network.client;

import java.io.IOException;

import cz.vesely.game.common.network.MapState;
import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
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
	public void read(NetInput in) throws IOException {
		this.mapState = MapState.values()[in.readInt()];
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeInt(this.mapState.ordinal());
	}

	@Override
	public void processPacket(INetHandlerServerLogin handler) {
		handler.handleMapResponse(handler);
	}

	public MapState getMapState() {
		return mapState;
	}

}

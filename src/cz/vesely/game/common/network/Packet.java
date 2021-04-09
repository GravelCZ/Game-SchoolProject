package cz.vesely.game.common.network;

import java.io.IOException;

public interface Packet<T extends INetHandler> {

	public void read(NetInput in) throws IOException;

	public void write(NetOutput out) throws IOException;

	void processPacket(T handler);
}

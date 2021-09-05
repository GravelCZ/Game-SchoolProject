package cz.vesely.game.common.network;

import com.esotericsoftware.kryo.KryoSerializable;

public interface Packet<T extends INetHandler> extends KryoSerializable {

	void processPacket(T handler);
}

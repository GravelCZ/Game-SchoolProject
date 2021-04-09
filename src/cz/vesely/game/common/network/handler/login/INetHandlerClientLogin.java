package cz.vesely.game.common.network.handler.login;

import cz.vesely.game.common.network.INetHandler;
import cz.vesely.game.common.network.server.PacketServerInventory;
import cz.vesely.game.common.network.server.PacketServerItemRegistery;
import cz.vesely.game.common.network.server.PacketServerLoginResponse;
import cz.vesely.game.common.network.server.PacketServerMap;
import cz.vesely.game.common.network.server.PacketServerPlayer;
import cz.vesely.game.common.network.server.PacketServerPlayerList;
import cz.vesely.game.common.network.server.PacketServerPlayerPlayerPosition;

public interface INetHandlerClientLogin extends INetHandler {

	void handleLoginResponse(PacketServerLoginResponse packet);

	void handleRegisterItems(PacketServerItemRegistery packet);

	void handlePlayerData(PacketServerPlayer packet);

	void handleInventory(PacketServerInventory packet);

	void handleMap(PacketServerMap packet);

	void handleLoginPosition(PacketServerPlayerPlayerPosition packet);

	void handlePlayerList(PacketServerPlayerList packetServerPlayerList);

}

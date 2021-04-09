package cz.vesely.game.common.network.handler.login;

import cz.vesely.game.common.network.INetHandler;
import cz.vesely.game.common.network.client.PacketClientLogin;

public interface INetHandlerServerLogin extends INetHandler {

	void handleLogin(PacketClientLogin packet);

	void handleMapResponse(INetHandlerServerLogin handler);

}

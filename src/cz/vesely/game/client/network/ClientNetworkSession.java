package cz.vesely.game.client.network;

import java.net.InetSocketAddress;

import cz.vesely.game.common.NetworkSystem;
import cz.vesely.game.common.network.NetworkHandler;
import cz.vesely.game.common.network.Protocol;

public class ClientNetworkSession {

	private NetworkHandler handler;
	
	public ClientNetworkSession() {
		Protocol.init();
	}

	public void connect(String address, int port) {
		handler = NetworkSystem.openChannel(new InetSocketAddress(address, port));
		handler.setPacketListener(new ClientLoginListener());
	}

	public NetworkHandler getHandler() {
		return handler;
	}
}

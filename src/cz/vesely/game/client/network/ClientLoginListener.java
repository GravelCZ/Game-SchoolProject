package cz.vesely.game.client.network;

import cz.vesely.game.client.GameLogic;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;
import cz.vesely.game.common.network.server.PacketServerInventory;
import cz.vesely.game.common.network.server.PacketServerItemRegistery;
import cz.vesely.game.common.network.server.PacketServerLoginResponse;
import cz.vesely.game.common.network.server.PacketServerMap;
import cz.vesely.game.common.network.server.PacketServerPlayer;
import cz.vesely.game.common.network.server.PacketServerPlayerList;
import cz.vesely.game.common.network.server.PacketServerPlayerPosition;

public class ClientLoginListener implements INetHandlerClientLogin {

	private GameLogic logic;
	
	public ClientLoginListener(GameLogic logic) {
		this.logic = logic;
	}
	
	@Override
	public void handleLoginResponse(PacketServerLoginResponse packet) {
		
	}

	@Override
	public void handleRegisterItems(PacketServerItemRegistery packet) {
		
	}

	@Override
	public void handlePlayerData(PacketServerPlayer packet) {
		
	}

	@Override
	public void handleInventory(PacketServerInventory packet) {
		
	}

	@Override
	public void handleMap(PacketServerMap packet) {
		
	}

	@Override
	public void handleLoginPosition(PacketServerPlayerPosition packet) {
		
	}

	@Override
	public void handlePlayerList(PacketServerPlayerList packetServerPlayerList) {
		
	}

	@Override
	public void onDisconnect(String s) {
		System.out.println("Disconnected: " + s);
		logic.setConnected(false);
	}
}

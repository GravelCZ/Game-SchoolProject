package cz.vesely.game.client.network;

import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;
import cz.vesely.game.common.network.server.PacketServerInventory;
import cz.vesely.game.common.network.server.PacketServerItemRegistery;
import cz.vesely.game.common.network.server.PacketServerLoginResponse;
import cz.vesely.game.common.network.server.PacketServerMap;
import cz.vesely.game.common.network.server.PacketServerPlayer;
import cz.vesely.game.common.network.server.PacketServerPlayerList;
import cz.vesely.game.common.network.server.PacketServerPlayerPlayerPosition;

public class ClientLoginListener implements INetHandlerClientLogin {

	@Override
	public void handleLoginResponse(PacketServerLoginResponse packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleRegisterItems(PacketServerItemRegistery packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePlayerData(PacketServerPlayer packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleInventory(PacketServerInventory packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMap(PacketServerMap packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleLoginPosition(PacketServerPlayerPlayerPosition packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePlayerList(PacketServerPlayerList packetServerPlayerList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect(String s) {
		System.out.println("Disconnected: " + s);
	}
}

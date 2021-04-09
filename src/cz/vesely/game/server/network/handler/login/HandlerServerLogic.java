package cz.vesely.game.server.network.handler.login;

import cz.vesely.game.common.network.client.PacketClientLogin;
import cz.vesely.game.common.network.handler.login.INetHandlerServerLogin;
import cz.vesely.game.server.ServerGameLogic;

public class HandlerServerLogic implements INetHandlerServerLogin {

	private ServerGameLogic serverLogic;
	
	public HandlerServerLogic(ServerGameLogic serverLogic) {
		this.serverLogic = serverLogic;
	}
	
	@Override
	public void handleLogin(PacketClientLogin packet) 
	{

	}

	@Override
	public void handleMapResponse(INetHandlerServerLogin handler) 
	{

	}

	@Override
	public void onDisconnect(String s) 
	{
		
	}
}

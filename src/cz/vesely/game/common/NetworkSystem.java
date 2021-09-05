package cz.vesely.game.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.vesely.game.common.network.NetworkHandler;
import cz.vesely.game.server.ServerGameLogic;

public class NetworkSystem {
	
	private final List<NetworkHandler> clients = Collections.synchronizedList(new ArrayList<>());
	
	private final ServerGameLogic logic;
	
	public NetworkSystem(ServerGameLogic logic) 
	{
		this.logic = logic;
	}
	
	public void createServerEndpoint(InetSocketAddress address) throws IOException, InterruptedException
	{
		
	}
	
	public static NetworkHandler openChannel(InetSocketAddress address)
	{
		NetworkHandler handler = new NetworkHandler();
		
		
		return handler;
	}
	
	public void terminateEndpoint()
	{
		
	}
}

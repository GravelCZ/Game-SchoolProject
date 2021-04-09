package cz.vesely.game.common.network.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.vesely.game.common.PlayerServerData;
import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerPlayerList implements Packet<INetHandlerClientLogin> {

	private List<PlayerServerData> players;
	
	public PacketServerPlayerList() {}
	
	public PacketServerPlayerList(List<PlayerServerData> players) {
		this.players = players;
	}
	
	@Override
	public void read(NetInput in) throws IOException 
	{
		this.players = new ArrayList<>();
		int len = in.readInt();
		
		for (int i = 0; i < len; i++)
		{
			int id = in.readInt();
			String name = in.readString();
			int health = in.readInt();
			float x = in.readFloat();
			float y = in.readFloat();
			
			players.add(new PlayerServerData(id, name, health, x, y));
		}
	}

	@Override
	public void write(NetOutput out) throws IOException 
	{
		out.writeInt(players.size());
		for (PlayerServerData ps : players)
		{
			out.writeInt(ps.getId());
			out.writeString(ps.getName());
			out.writeInt(ps.getHealth());
			out.writeFloat(ps.getPosition().x);
			out.writeFloat(ps.getPosition().y);
		}
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) 
	{
		handler.handlePlayerList(this);
	}

}

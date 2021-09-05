package cz.vesely.game.common.network.server;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cz.vesely.game.common.PlayerServerData;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerPlayerList implements Packet<INetHandlerClientLogin> {

	private List<PlayerServerData> players;
	
	public PacketServerPlayerList() {}
	
	public PacketServerPlayerList(List<PlayerServerData> players) {
		this.players = players;
	}
	

	@Override
	public void write(Kryo kryo, Output output) {
		output.writeInt(players.size());
		for (PlayerServerData ps : players)
		{
			output.writeInt(ps.getId());
			output.writeString(ps.getName());
			output.writeInt(ps.getHealth());
			output.writeFloat(ps.getPosition().x);
			output.writeFloat(ps.getPosition().y);
		}
	}

	@Override
	public void read(Kryo kryo, Input input) 
	{
		this.players = new ArrayList<>();
		int len = input.readInt();
		
		for (int i = 0; i < len; i++)
		{
			int id = input.readInt();
			String name = input.readString();
			int health = input.readInt();
			float x = input.readFloat();
			float y = input.readFloat();
			
			players.add(new PlayerServerData(id, name, health, x, y));
		}
	}


	@Override
	public void processPacket(INetHandlerClientLogin handler) 
	{
		handler.handlePlayerList(this);
	}

}

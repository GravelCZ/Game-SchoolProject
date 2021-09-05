package cz.vesely.game.common.network.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cz.vesely.game.common.PlayerLoginInformation;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerPlayer implements Packet<INetHandlerClientLogin> {

	private PlayerLoginInformation player;

	public PacketServerPlayer() {
	}

	public PacketServerPlayer(PlayerLoginInformation player) {
		this.player = player;
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handlePlayerData(this);
	}

	public PlayerLoginInformation getPlayer() {
		return player;
	}

	@Override
	public void write(Kryo kryo, Output output) 
	{
		output.writeInt(player.getId());
		output.writeInt(player.getAttack());
		output.writeInt(player.getDefence());
		output.writeInt(player.getHealth());
	}

	@Override
	public void read(Kryo kryo, Input input) 
	{
		int id = input.readInt();
		int attack = input.readInt();
		int defence = input.readInt();
		int health = input.readInt();
		
		player = new PlayerLoginInformation(id, attack, defence, health);
	}

}

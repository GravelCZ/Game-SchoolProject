package cz.vesely.game.common.network.server;

import java.io.IOException;

import cz.vesely.game.common.PlayerLoginInformation;
import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
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
	public void read(NetInput in) throws IOException {
		int id = in.readInt();
		int attack = in.readInt();
		int defence = in.readInt();
		int health = in.readInt();

		this.player = new PlayerLoginInformation(id, attack, defence, health);
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeInt(player.getId());
		out.writeInt(player.getAttack());
		out.writeInt(player.getDefence());
		out.writeInt(player.getHealth());
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handlePlayerData(this);
	}

	public PlayerLoginInformation getPlayer() {
		return player;
	}

}

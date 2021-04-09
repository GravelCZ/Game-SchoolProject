package cz.vesely.game.common.network.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.vesely.game.common.Weapon;
import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerItemRegistery implements Packet<INetHandlerClientLogin> {

	private List<Weapon> weapons;

	public PacketServerItemRegistery() {
	}

	public PacketServerItemRegistery(List<Weapon> weapons) {
		this.weapons = weapons;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.weapons = new ArrayList<>();
		int len = in.readInt();
		for (int i = 0; i < len; i++) {
			byte id = in.readByte();
			String name = in.readString();
			int attack = in.readInt();
			int defence = in.readInt();
			int level = in.readInt();

			Weapon w = new Weapon(name, attack, defence, level);
			w.setId(id);

			this.weapons.add(w);
		}
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeInt(weapons.size());

		for (Weapon w : weapons) {
			out.writeByte(w.getId());
			out.writeString(w.getName());
			out.writeInt(w.getAttack());
			out.writeInt(w.getDefence());
			out.writeInt(w.getLevel());
		}
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleRegisterItems(this);
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}
}

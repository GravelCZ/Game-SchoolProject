package cz.vesely.game.common.network.server;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cz.vesely.game.common.Weapon;
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
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleRegisterItems(this);
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}

	@Override
	public void write(Kryo kryo, Output output) 
	{
		output.writeInt(weapons.size());
		for (Weapon w : weapons) 
		{
			output.writeString(w.getName());
			output.writeInt(w.getAttack());
			output.writeInt(w.getDefence());
			output.writeInt(w.getLevel());
		}
	}

	@Override
	public void read(Kryo kryo, Input input) 
	{
		this.weapons = new ArrayList<>();
		int len = input.readInt();
		for (int i = 0; i < len; i++) 
		{
			String name = input.readString();
			int attack = input.readInt();
			int defence = input.readInt();
			int level = input.readInt();
			
			weapons.add(new Weapon(name, attack, defence, level));
		}
	}
}

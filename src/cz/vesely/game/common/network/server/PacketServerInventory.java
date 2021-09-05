package cz.vesely.game.common.network.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.handler.login.INetHandlerClientLogin;

public class PacketServerInventory implements Packet<INetHandlerClientLogin> {

	private byte leftHand, rightHand;

	public PacketServerInventory() {
	}

	public PacketServerInventory(byte leftHand, byte rightHand) {
		this.leftHand = leftHand;
		this.rightHand = rightHand;
	}

	@Override
	public void processPacket(INetHandlerClientLogin handler) {
		handler.handleInventory(this);
	}

	public byte getLeftHand() {
		return leftHand;
	}

	public byte getRightHand() {
		return rightHand;
	}

	@Override
	public void write(Kryo kryo, Output output) 
	{
		output.writeByte(leftHand);
		output.writeByte(rightHand);
	}

	@Override
	public void read(Kryo kryo, Input input) 
	{
		this.leftHand = input.readByte();
		this.rightHand = input.readByte();
	}

}

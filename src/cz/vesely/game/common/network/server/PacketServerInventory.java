package cz.vesely.game.common.network.server;

import java.io.IOException;

import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
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
	public void read(NetInput in) throws IOException {
		this.leftHand = (byte) in.readUnsignedByte();
		this.rightHand = (byte) in.readUnsignedByte();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(leftHand);
		out.writeByte(rightHand);
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

}

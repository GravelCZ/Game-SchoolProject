package cz.vesely.game.common.network;

import java.io.IOException;

public interface NetInput {

	public boolean readBoolean() throws IOException;

	public byte readByte() throws IOException;

	public int readUnsignedByte() throws IOException;

	public int readInt() throws IOException;

	public int readVarInt() throws IOException;

	public long readLong() throws IOException;

	public float readFloat() throws IOException;

	public double readDouble() throws IOException;

	public byte[] readPrefixedBytes() throws IOException;

	public byte[] readBytes(int len) throws IOException;

	public int readBytes(byte[] target) throws IOException;

	public int readBytes(byte b[], int offset, int length) throws IOException;

	public String readString() throws IOException;

	public int available() throws IOException;

}

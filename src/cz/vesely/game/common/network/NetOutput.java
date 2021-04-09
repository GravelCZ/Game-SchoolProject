package cz.vesely.game.common.network;

import java.io.IOException;

public interface NetOutput {

	public void writeBoolean(boolean b) throws IOException;

	public void writeByte(int b) throws IOException;

	public void writeInt(int i) throws IOException;

	public void writeVarInt(int i) throws IOException;

	public void writeLong(long l) throws IOException;

	public void writeFloat(float f) throws IOException;

	public void writeDouble(double d) throws IOException;

	public void writePrefixedBytes(byte[] data) throws IOException;

	public void writeBytes(byte[] data) throws IOException;

	public void writeString(String s) throws IOException;

	public void flush() throws IOException;
}

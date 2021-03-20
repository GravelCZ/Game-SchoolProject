package cz.vesely.game.client.network;

import java.io.IOException;

import cz.vesely.game.common.network.NetInput;
import io.netty.buffer.ByteBuf;

public class ByteBufNetInput implements NetInput {

	private ByteBuf buf;
	
	public ByteBufNetInput(ByteBuf buf) 
	{
		this.buf = buf;
	}

	@Override
	public boolean readBoolean() throws IOException {
		return buf.readBoolean();
	}

	@Override
	public byte readByte() throws IOException {
		return buf.readByte();
	}

	@Override
	public int readUnsignedByte() throws IOException {
		return buf.readUnsignedByte();
	}

	@Override
	public int readInt() throws IOException {
		return buf.readInt();
	}

	@Override
	public int readVarInt() throws IOException {
		int value = 0;
		int size = 0;
		int b;
		while (((b = readByte()) & 0x80) == 0x80) {
			value |= (b & 0x7F) << (size ++ * 7);
			if (size > 5)
			{
				throw new IOException("VarInt too long");
			}
		}
		
		return value | ((b & 0x7F) << (size * 7));
	}

	@Override
	public long readLong() throws IOException {
		return this.buf.readLong();
	}

	@Override
	public float readFloat() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double readDouble() throws IOException {
		return this.buf.readLong();
	}

	@Override
	public byte[] readPrefixedBytes() throws IOException {
		short len = this.buf.readShort();
		return this.readBytes(len);
	}

	@Override
	public byte[] readBytes(int len) throws IOException {
		if (len < 0) {
			return null;
		}
		
		byte[] b = new byte[len];
		this.buf.readBytes(b);
		return b;
	}

	@Override
	public int readBytes(byte[] b) throws IOException {
		return this.readBytes(b, 0, b.length);
	}

	@Override
	public int readBytes(byte[] b, int offset, int length) throws IOException {
		int readable = this.buf.readableBytes();
		if (readable <= 0) {
			return -1;
		}
		
		if (readable < length) {
			length = readable;
		}
		
		this.buf.readBytes(b, offset, length);
		return length;
	}
	
	@Override
	public String readString() throws IOException {
		int len = readVarInt();
		byte[] data = this.readBytes(len);
		return new String(data, "UTF-8");
	}

	@Override
	public int available() throws IOException {
		return this.buf.readableBytes();
	}

}

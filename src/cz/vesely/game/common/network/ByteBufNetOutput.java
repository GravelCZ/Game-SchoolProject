package cz.vesely.game.common.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class ByteBufNetOutput implements NetOutput {

	private ByteBuf buf;
	
	public ByteBufNetOutput(ByteBuf out) 
	{
		this.buf = out;
	}

	@Override
	public void writeBoolean(boolean b) throws IOException 
	{
		this.buf.writeBoolean(b);
	}

	@Override
	public void writeByte(int b) throws IOException 
	{
		this.buf.writeByte(b);
	}

	@Override
	public void writeInt(int i) throws IOException 
	{
		this.buf.writeInt(i);
	}

	@Override
	public void writeVarInt(int i) throws IOException 
	{
		while ((i & -0x7F) != 0) {
			this.writeByte((i & 0x7F) | 0x80);
			i >>>= 7;
		}
		
		writeByte(i);
	}

	@Override
	public void writeLong(long l) throws IOException 
	{
		this.buf.writeLong(l);
	}

	@Override
	public void writeFloat(float f) throws IOException 
	{
		this.buf.writeFloat(f);
	}

	@Override
	public void writeDouble(double d) throws IOException 
	{
		this.buf.writeDouble(d);
	}

	@Override
	public void writePrefixedBytes(byte[] data) throws IOException 
	{
		this.buf.writeShort(data.length);
		this.buf.writeBytes(data);
	}

	@Override
	public void writeBytes(byte[] data) throws IOException 
	{
		this.buf.writeBytes(data);
	}

	@Override
	public void writeString(String s) throws IOException 
	{
		if (s == null) {
			return;
		}
		
		byte[] bytes = s.getBytes();
		if (bytes.length > 32767)
		{
			return;
		}
		
		writeVarInt(bytes.length);
		writeBytes(bytes);
	}

	@Override
	public void flush() throws IOException {}

}

package cz.vesely.game.common.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.CorruptedFrameException;

public class PacketSizer extends ByteToMessageCodec<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int len = msg.readableBytes();
		out.ensureWritable(Integer.BYTES + len);
		System.out.println("Writing: " + len + 4);
		
		out.writeInt(len);
		out.writeBytes(msg);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		int lenSize = Integer.BYTES;

		buf.markReaderIndex();

		byte[] lengthBytes = new byte[lenSize];

		for (int i = 0; i < lenSize; i++) {
			if (!buf.isReadable()) {
				System.out.println("Buffer not readable!");
				buf.resetReaderIndex();
				return;
			}

			lengthBytes[i] = buf.readByte();
			System.out.println((char) lengthBytes[i]);
			if (i == lenSize - 1) {
				int len = Unpooled.wrappedBuffer(lengthBytes).readInt();
				System.out.println("Len: " + len);
				if (buf.readableBytes() < len) {
					buf.resetReaderIndex();
					return;
				}
				
				out.add(buf.readBytes(len));
				return;
			}
		}
		
		System.out.println("corupted frame!");
		throw new CorruptedFrameException("Length is too long or invalid");
	}

}

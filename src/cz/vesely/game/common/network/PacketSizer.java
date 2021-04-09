package cz.vesely.game.common.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class PacketSizer extends ByteToMessageCodec<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int len = msg.readableBytes();
		out.ensureWritable(Integer.BYTES + len);

		out.writeInt(len);
		out.writeBytes(msg);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		buf.markReaderIndex();
		if (!buf.isReadable()) {
			buf.resetReaderIndex();
			return;
		}

		int packetLength = buf.readInt();

		if (buf.readableBytes() < packetLength) {
			buf.resetReaderIndex();
			return;
		}

		out.add(buf.readBytes(packetLength));
	}

}

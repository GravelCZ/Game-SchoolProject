package cz.vesely.game.common.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class PacketCodec extends ByteToMessageCodec<Packet<INetHandler>> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet<INetHandler> packet, ByteBuf buf) throws Exception {
		NetOutput out = new ByteBufNetOutput(buf);
		int id = Protocol.getOutgoingID(packet.getClass());
		System.out.println("outgoing id: " + id);
		out.writeByte(id);
		packet.write(out);
		out.writeByte(0x01);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		buf.markReaderIndex();

		try {
			NetInput in = new ByteBufNetInput(buf);
			int id = in.readByte();
			if (!Protocol.isValid(id)) {
				buf.resetReaderIndex();
				return;
			}
			Packet<?> packet = Protocol.createIncomingPacket(id);
			packet.read(in);

			byte end = in.readByte();
			if (end != 0x01) {
				throw new IllegalStateException("Packet does not end with 0x01");
			}
			if (buf.readableBytes() > 0) {
				throw new IllegalStateException("Packet " + packet.getClass() + " not fully read!");
			}
		} catch (Throwable e) {
			buf.readerIndex(buf.readerIndex() + buf.readableBytes());
			e.printStackTrace(System.err);
		}
	}

}

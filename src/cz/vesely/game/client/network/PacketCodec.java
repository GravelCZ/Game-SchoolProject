package cz.vesely.game.client.network;

import java.util.List;

import cz.vesely.game.common.network.ByteBufNetOutput;
import cz.vesely.game.common.network.NetInput;
import cz.vesely.game.common.network.NetOutput;
import cz.vesely.game.common.network.Packet;
import cz.vesely.game.common.network.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class PacketCodec extends ByteToMessageCodec<Packet> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf buf) throws Exception 
	{
		NetOutput out = new ByteBufNetOutput(buf);
		int id = Protocol.getOutgoingID(packet.getClass());
		out.writeByte(id);
		packet.write(out);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception 
	{
		buf.markReaderIndex();
		
		try {
			NetInput in = new ByteBufNetInput(buf);
			int id = in.readByte();
			if (!Protocol.isValid(id)) {
				buf.resetReaderIndex();
				return;
			}
			Packet packet = Protocol.createIncomingPacket(id);
			packet.read(in);
			
			if (buf.readableBytes() > 0) {
				throw new IllegalStateException("Packet " + packet.getClass() + " not fully read!");
			}
		} catch (Throwable e) {
			buf.readerIndex(buf.readerIndex() + buf.readableBytes());
			e.printStackTrace(System.err);
		} 
	}


}

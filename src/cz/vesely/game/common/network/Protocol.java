package cz.vesely.game.common.network;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import cz.vesely.game.common.network.client.PacketClientLogin;
import cz.vesely.game.common.network.client.PacketClientMapResponse;
import cz.vesely.game.common.network.server.PacketServerDisconnect;
import cz.vesely.game.common.network.server.PacketServerInventory;
import cz.vesely.game.common.network.server.PacketServerItemRegistery;
import cz.vesely.game.common.network.server.PacketServerLoginResponse;
import cz.vesely.game.common.network.server.PacketServerMap;
import cz.vesely.game.common.network.server.PacketServerPlayer;
import cz.vesely.game.common.network.server.PacketServerPlayerList;
import cz.vesely.game.common.network.server.PacketServerPlayerPosition;

public class Protocol {

	private static final Map<Integer, Class<? extends Packet<?>>> in = new HashMap<>();
	private static final Map<Class<? extends Packet<?>>, Integer> out = new HashMap<>();

	private static int packetIndex = 0;
	
	public static void init() 
	{
		//client
		register(counter(), PacketClientLogin.class);
		register(counter(), PacketClientMapResponse.class);
		
		//server
		register(counter(), PacketServerLoginResponse.class);
		register(counter(), PacketServerItemRegistery.class);
		register(counter(), PacketServerPlayer.class);
		register(counter(), PacketServerInventory.class);
		register(counter(), PacketServerMap.class);
		register(counter(), PacketServerPlayerPosition.class);
		register(counter(), PacketServerPlayerList.class);
		register(counter(), PacketServerDisconnect.class);
		
		
	}

	private static int counter()
	{
		return packetIndex++;
	}
	
	private static void register(int id, Class<? extends Packet<?>> p) {
		in.put(id, p);
		out.put(p, id);
	}

	public static Packet<?> createIncomingPacket(int id) {
		if (id < 0 || !in.containsKey(id) || in.get(id) == null) {
			return null;
		}

		Class<? extends Packet<?>> packet = in.get(id);
		try {
			Constructor<? extends Packet<?>> constructor = packet.getDeclaredConstructor();
			if (!constructor.isAccessible()) {
				constructor.setAccessible(true);
			}

			return constructor.newInstance();
		} catch (NoSuchMethodError e) {
			throw new IllegalStateException(
					"Packet " + id + ": " + packet.getName() + " does not have a no params constructor.");
		} catch (Exception e) {
			throw new IllegalStateException("Failed to instantite packet" + id + ": " + packet.getName(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static int getOutgoingID(Class<? extends Packet> packet) {
		if (!out.containsKey(packet) || out.get(packet) == null) {
			return -1;
		}

		return out.get(packet);
	}

	public static boolean isValid(int id) {
		return id >= 0 && in.containsKey(id);
	}
}

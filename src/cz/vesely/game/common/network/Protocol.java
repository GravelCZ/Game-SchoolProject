package cz.vesely.game.common.network;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Protocol {

	private static final Map<Integer, Class<? extends Packet>> in = new HashMap<>();
	private static final Map<Class<? extends Packet>, Integer> out = new HashMap<>();
	
	public static void init() 
	{
		
	}

	private static void register(int id, Class<? extends Packet> p) {
		in.put(id, p);
		out.put(p, id);
	}
	
	public static Packet createIncomingPacket(int id)
	{
		if (id < 0 || !in.containsKey(id) || in.get(id) == null) {
			return null;
		}
		
		Class<? extends Packet> packet = in.get(id);
		try {
			Constructor<? extends Packet> constructor = packet.getDeclaredConstructor();
			if (!constructor.isAccessible()) {
				constructor.setAccessible(true);
			}
			
			return constructor.newInstance();
		} catch (NoSuchMethodError e) {
			throw new IllegalStateException("Packet " + id + ": " + packet.getName() + " does not have a no params constructor.");
		} catch (Exception e) {
			throw new IllegalStateException("Failed to instantite packet" + id + ": " + packet.getName(), e);
		}
	}
	
	public static int getOutgoingID(Class<? extends Packet> packet)
	{
		if (!out.containsKey(packet) || out.get(packet) == null) {
			return -1;
		}
		
		return out.get(packet);
	}
	
	public static boolean isValid(int id)
	{
		return id >= 0 && in.containsKey(id);
	}
}

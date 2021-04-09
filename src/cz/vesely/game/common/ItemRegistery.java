package cz.vesely.game.common;

import java.util.HashMap;
import java.util.Optional;

public class ItemRegistery {

	private static HashMap<Byte, Weapon> weaponRegistery = new HashMap<>();
	private static int id;

	// volá se pouze na serveru
	public static void init() {
		// TODO: read from file
		Weapon rookieSword = new Weapon("Rookie sword", 5, 0, 1);
		register(rookieSword);
	}

	public static void register(Weapon w) {
		if (id + 1 > 256) {
			weaponRegistery.put((byte) id, w);
			id++;
			w.setId(id);
		} else {
			throw new RuntimeException("Not enough space for weapons: " + w);
		}
	}

	public static Weapon getById(byte id) {
		return weaponRegistery.get(id);
	}

	public static Optional<Weapon> getByName(String name) {
		return weaponRegistery.entrySet().stream().filter(e -> e.getValue().getName().equalsIgnoreCase(name))
				.map(e -> e.getValue()).findAny();
	}
}

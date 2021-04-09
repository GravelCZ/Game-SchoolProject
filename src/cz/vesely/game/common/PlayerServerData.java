package cz.vesely.game.common;

import org.joml.Vector2f;

public class PlayerServerData {

	private int id;
	private String name;
	
	protected Vector2f position;
	
	private int health;
	
	public PlayerServerData(int id, String name, int health, float x, float y) {
		this.id = id;
		this.name = name;
		this.health = health;
		this.position = new Vector2f(x, y);
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Vector2f getPosition() {
		return position;
	}

}

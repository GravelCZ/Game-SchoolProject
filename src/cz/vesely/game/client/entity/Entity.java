package cz.vesely.game.client.entity;

import org.joml.Vector3f;

public abstract class Entity extends GameObject {

	public Entity(Vector3f position, float scale, String texture) throws Exception {
		super(position, scale, texture);
	}
	
}

package cz.vesely.game.common;

import org.joml.Vector2f;

public class Collision {

	private Vector2f dst;
	private boolean intersects;
	
	public Collision(Vector2f dst, boolean intersects) {
		this.dst = dst;
		this.intersects = intersects;
	}
	
	
	public Vector2f getDistance() {
		return dst;
	}
	
	public boolean isIntersecting() {
		return intersects;
	}
}

package cz.vesely.game.common;

import org.joml.Vector2f;

public class AABB {

	private Vector2f middle, size;

	public AABB(Vector2f middle, Vector2f size) {
		this.middle = middle;
		this.size = size;
	}

	public Collision collides(AABB aabb) {
		Vector2f dst = aabb.middle.sub(middle, new Vector2f());
		dst.x = (float) Math.abs(dst.x);
		dst.y = (float) Math.abs(dst.y);

		dst.sub(size.add(aabb.size, new Vector2f()));

		return new Collision(dst, dst.x < 0 && dst.y < 0);
	}

	public void correct(AABB aabb, Collision from) {
		Vector2f correct = aabb.middle.sub(middle, new Vector2f());
		if (from.getDistance().x > from.getDistance().y) {
			middle.add(correct.x > 0 ? from.getDistance().x : -from.getDistance().x, 0);
		} else {
			middle.add(0, correct.y > 0 ? from.getDistance().y : -from.getDistance().y);
		}
	}

	public Vector2f getMiddle() {
		return middle;
	}

	public Vector2f getSize() {
		return size;
	}
}

package cz.vesely.game.client.entity;

import java.util.EnumMap;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import cz.vesely.game.client.render.StaticModel;
import cz.vesely.game.common.AABB;
import cz.vesely.game.common.EnumHand;
import cz.vesely.game.common.Weapon;

public class PlayerClient extends GameObject {

	private int id;
	private int strengthAttack;
	private int strengthDefence;
	private int health;

	private float moveStep = 0.03f;

	private String name;

	private Vector3f lastPosition;
	private int updatePosX, updatePosY;

	private EnumMap<EnumHand, Weapon> inventory;

	private boolean facingLeft = false;

	private AABB aabb;

	public PlayerClient(String name, Vector3f position) throws Exception {
		super(position, 0.4f, "textures/player.png");
		this.name = name;

		inventory = new EnumMap<>(EnumHand.class);
		this.model = StaticModel.playerModel;
		this.aabb = new AABB(new Vector2f(this.position.x, this.position.y), new Vector2f(1, 2));
	}

	@Override
	public void update(float interval) {
		this.lastPosition = position;

		this.facingLeft = updatePosX == -1;
		this.position.add(updatePosX * moveStep, updatePosY * moveStep, 0);
		this.updatePosX = 0;
		this.updatePosY = 0;
	}

	@Override
	public void render(Matrix4f projeciton) {
		getProgram().setUniform("flip", this.facingLeft ? 1 : 0);
		super.render(projeciton);
	}

	public void updatePosition(int x, int y) {
		this.updatePosX = x;
		this.updatePosY = y;
	}

}

package cz.vesely.game.client.entity;

import org.joml.Vector3f;

public class PlayerClient extends GameObject {

	private int id;
	private int strengthAttack;
	private int strengthDefence;
	private int health;
	
	private float cameraStep = 0.05f;
	
	private Vector3f lastPosition;
	private Vector3f updatePosition;
	
	public PlayerClient(String name, Vector3f position) throws Exception {
		super(name, position, 1f, "panacek.png");
	}

	@Override
	public void update(float interval) 
	{
		this.lastPosition = position;
		
		this.position.add(updatePosition.mul(cameraStep));
		this.updatePosition = new Vector3f(0f);
		
		
	}

	public void updatePosition(Vector3f positionInc) 
	{
		this.updatePosition = positionInc;
	}
	
	
	
	
	
}

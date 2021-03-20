package cz.vesely.game.client.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

	private Vector3f position;
	private Matrix4f projection;
	private Matrix4f invertedProjection;
	
	private int width, height;
	
	public Camera() 
	{
		this(new Vector3f(0f));
	}
	
	public Camera(Vector3f position)
	{
		this.position = position;
	}
	
	public void calculateProjection(Window w)
	{
		this.width = w.getWidth();
		this.height = w.getHeight();
		this.projection = new Matrix4f().ortho2D(-w.getWidth() / 2f, w.getWidth() / 2f, -w.getHeight() / 2f, w.getHeight() / 2f).scale(40f);
		this.invertedProjection = this.projection.invertOrtho(new Matrix4f());
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Matrix4f getInvertedProjection() {
		return invertedProjection;
	}
	
	public Matrix4f getProjection()
	{
		return projection.translate(position, new Matrix4f());
	}
	
	public void movePosition(float x, float y, float z)
	{
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}
	
	public void setPosition(float x, float y, float z) 
	{
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
}


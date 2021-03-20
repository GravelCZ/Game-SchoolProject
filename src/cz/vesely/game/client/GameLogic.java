package cz.vesely.game.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import cz.vesely.game.client.entity.GameObject;
import cz.vesely.game.client.entity.PlayerClient;
import cz.vesely.game.client.render.Camera;
import cz.vesely.game.client.render.Window;
import cz.vesely.game.client.render.renderers.GameRenderer;

public class GameLogic {
	
	private GameRenderer render;
	
	private Camera camera;
	
	private int posIncX, posIncY;
	
	private List<GameObject> objects;
	
	private GameState state = GameState.LOGIN_MENU;
	
	private PlayerClient self;
	private World world;
	
	private float mouseScreenX, mouseScreenY;
	
	public GameLogic() 
	{
		this.render = new GameRenderer();
		this.camera = new Camera();
		
		this.objects = new ArrayList<>();
	}
	
	public void init(Window window, MouseInput input) throws Exception 
	{
		render.init(window, this);
		//this.render.setGUI(new LoginGUI());
		this.self = new PlayerClient("", new Vector3f());
		this.world = new World("textures/world1.png");
	}

	public void update(float interval) 
	{
		if (this.state == GameState.LOGIN_MENU) {
			connectToServer("127.0.0.1", 6965);
			this.state = GameState.GAME;
		}
		
		self.updatePosition(posIncX, posIncY);
		
		self.update(interval);	
		
		
		// TODO: update heních objektů, a sítě
	}

	private void connectToServer(String addr, int port) 
	{
		
	}

	public void render(Window window) 
	{
		this.render.render(window, this);
	}

	public void input(Window window, MouseInput input) 
	{
		this.posIncX = 0;
		this.posIncY = 0;
		if (window.isKeyPressed(GLFW_KEY_W)) {
			posIncY = 1;
		} 
		if (window.isKeyPressed(GLFW_KEY_S)) {
			posIncY = -1;
		}
		if (window.isKeyPressed(GLFW_KEY_A)) {
			posIncX = -1;
		}
		if (window.isKeyPressed(GLFW_KEY_D)) {
			posIncX = 1;
		}

		if (input.isInWindow()) {
			float sx = (float) (input.getCurrPos().x / window.getWidth());
			float sy = (float) (input.getCurrPos().y / window.getHeight());
			
			//System.out.println(sx + " : " + -sy);
			
			this.mouseScreenX = sx;
			this.mouseScreenY = sy;
			
			//Vector4f positionInWorldSpace = new Vector4f(sx * 2.0f - 1f, sy * -2f + 1f, 0f, 1f).mul(camera.getInvertedProjection());
			
			
		}
	}

	public void cleanUp() 
	{
		this.render.cleanUp();
		objects.forEach(GameObject::cleanUp);
		if (this.self != null) {
			this.self.cleanUp();
		}
		if (this.world != null) {
			this.world.cleanUp();
		}
	}
	
	public float getMouseScreenX() {
		return mouseScreenX;
	}
	
	public float getMouseScreenY() {
		return mouseScreenY;
	}
	
	public GameState getState() {
		return state;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}

	public PlayerClient getSelfPlayer() {
		return this.self;
	}

	public World getWorld() {
		return this.world;
	}

	
}
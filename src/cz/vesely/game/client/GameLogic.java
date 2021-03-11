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
	
	private Vector3f positionInc;
	
	private List<GameObject> objects;
	
	//private GameState state = GameState.MENU;
	
	private PlayerClient self;
	private World world;
	
	public GameLogic() 
	{
		this.render = new GameRenderer();
		this.camera = new Camera();
		this.positionInc = new Vector3f();
		
		this.objects = new ArrayList<>();
	}
	
	public void init(Window window, MouseInput input) throws Exception 
	{
		this.self = new PlayerClient("Test", new Vector3f());
		render.init();
		
	}

	public void update(float interval) 
	{
		self.updatePosition(positionInc);
		
		self.update(interval);	
		
		
		//camera.movePosition(positionInc.x * 0.05f, positionInc.y * 0.05f, 0);
		// TODO: update heních objektů, a sítě
	}

	public void render(Window window) 
	{
		//this.world.render();	
		
		this.render.render(window, this);
	}

	public void input(Window window, MouseInput input) 
	{
		this.positionInc = new Vector3f(0);
		if (window.isKeyPressed(GLFW_KEY_W)) {
			positionInc.y = 1;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			positionInc.y = -1;
		}

		if (window.isKeyPressed(GLFW_KEY_A)) {
			positionInc.x = -1;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			positionInc.x = 1;
		}
		
		
	}

	public void cleanUp() 
	{
		this.render.cleanUp();
		objects.forEach(GameObject::cleanUp);
		//self.cleanUp();
		//world.cleanUp();
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

	
}
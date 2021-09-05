package cz.vesely.game.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joml.Vector3f;

import cz.vesely.game.client.entity.GameObject;
import cz.vesely.game.client.entity.PlayerClient;
import cz.vesely.game.client.gui.AbstractGui;
import cz.vesely.game.client.gui.LoginGUI;
import cz.vesely.game.client.network.ClientLoginListener;
import cz.vesely.game.client.render.Camera;
import cz.vesely.game.client.render.Window;
import cz.vesely.game.client.render.renderers.GameRenderer;
import cz.vesely.game.common.NetworkSystem;
import cz.vesely.game.common.network.NetworkHandler;
import cz.vesely.game.common.network.Protocol;
import cz.vesely.game.common.network.client.PacketClientLogin;

public class GameLogic {

	private final GameRenderer render;

	private final Camera camera;

	private int posIncX, posIncY;

	private List<GameObject> objects;

	private GameState state = GameState.MENU;

	private PlayerClient self;
	private World world;

	private float mouseScreenX, mouseScreenY;

	private final Window window;

	private NetworkHandler handler;
	private boolean connecting = false;
	private boolean connected = false;
	
	public GameLogic(Window window) {
		Logger.getLogger("io.netty").setLevel(Level.FINEST);
		this.window = window;
		this.render = new GameRenderer();
		this.camera = new Camera();

		this.objects = new ArrayList<>();
	}

	public void init() throws Exception {
		render.init(window, this);
		// this.render.setGUI(new LoginGUI());
		this.self = new PlayerClient("", new Vector3f());
		this.world = new World("textures/world1.png");
		setGui(new LoginGUI());
	}

	public void update(float interval) {
		if (this.state == GameState.MENU) {
			Protocol.init();
			if (!connecting) {
				handler = NetworkSystem.openChannel(new InetSocketAddress("127.0.0.1", 6930));
				handler.setPacketListener(new ClientLoginListener(this));
				handler.sendPacket(new PacketClientLogin("GravelCZ", 1));
				this.connecting = true;
				this.connected = true;
			}
		} else if (this.state == GameState.GAME) {
			self.updatePosition(posIncX, posIncY);

			self.update(interval);
		}
		
		if (this.connected) {
			//handler.networkTick();	
		}
		// TODO: update heních objektů, a sítě
	}
	
	public void render() {
		this.render.render(window, this);
	}

	public void input(MouseInput input) {
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

			// System.out.println(sx + " : " + -sy);

			this.mouseScreenX = sx;
			this.mouseScreenY = sy;

			// Vector4f positionInWorldSpace = new Vector4f(sx * 2.0f - 1f, sy * -2f + 1f,
			// 0f, 1f).mul(camera.getInvertedProjection());

			if (input.leftPressed()) {
				render.getGuiRender().input((int) (sx * window.getWidth()), (int) (sy * window.getHeight()));
			}
		}
	}

	public void cleanUp() {
		this.render.cleanUp();
		objects.forEach(GameObject::cleanUp);
		this.handler.disconnect("");
		if (this.self != null) {
			this.self.cleanUp();
		}
		if (this.world != null) {
			this.world.cleanUp();
		}
	}

	public void setGui(AbstractGui gui) {
		window.setInputCallback((i) -> {
			gui.inputKey((char) i.intValue());
		});
		render.setGUI(gui);
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

	public Window getWindow() {
		return window;
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
	
	public void setConnected(boolean b) {
		this.connected = b;
	}

}
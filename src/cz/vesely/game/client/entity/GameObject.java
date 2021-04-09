package cz.vesely.game.client.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import cz.vesely.game.client.render.IRenderable;
import cz.vesely.game.client.render.Model;
import cz.vesely.game.client.render.ShaderProgram;
import cz.vesely.game.client.render.StaticModel;
import cz.vesely.game.client.render.Texture;

public abstract class GameObject implements IRenderable {

	protected Model model;
	private Texture texture;
	private ShaderProgram program;

	protected Vector3f position;
	private float scale;

	public GameObject(Vector3f position, float scale, String texture) throws Exception {
		this.program = new ShaderProgram("main");

		this.program.createUniform("sampler");
		this.program.createUniform("projection");
		this.program.createUniform("position");
		this.program.createUniform("flip");
		this.texture = new Texture(texture);

		this.model = StaticModel.basicModel;
		this.position = position;
		this.scale = scale;
	}

	public abstract void update(float interval);

	@Override
	public void render(Matrix4f projection) {
		this.program.setUniform("projection", projection);
		this.model.render();
	}

	public ShaderProgram getProgram() {
		return program;
	}

	public Texture getTexture() {
		return texture;
	}

	public void cleanUp() {
		texture.cleanup();
		program.cleanup();
		model.cleanup();
	}

	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Matrix4f calculatePositionAndScale() {
		return new Matrix4f().translate(this.position).scale(this.scale);
	}
}

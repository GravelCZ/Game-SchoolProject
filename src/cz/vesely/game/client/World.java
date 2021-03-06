package cz.vesely.game.client;

import org.joml.Matrix4f;

import cz.vesely.game.client.render.IRenderable;
import cz.vesely.game.client.render.Model;
import cz.vesely.game.client.render.ShaderProgram;
import cz.vesely.game.client.render.StaticModel;
import cz.vesely.game.client.render.Texture;

public class World implements IRenderable {

	private Model model;
	private Texture texture;
	private ShaderProgram program;

	private boolean loaded = false;

	public World(String pathToImage) throws Exception {
		this.texture = new Texture(pathToImage);
		this.program = new ShaderProgram("world/world");
		this.program.createUniform("sampler");
		// this.program.createUniform("projection");
		this.model = StaticModel.basicModel;
		this.loaded = true;
	}

	@Override
	public void render(Matrix4f projection) {
		if (loaded) {
			program.bind();

			program.setUniform("sampler", 0);
			// program.setUniform("projection", projection);

			texture.bind(0);
			model.render();
			program.unbind();
		}
	}

	public void cleanUp() {
		this.model.cleanup();
		this.texture.cleanup();
		this.program.cleanup();
	}

}

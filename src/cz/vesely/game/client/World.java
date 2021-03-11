package cz.vesely.game.client;

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
	
	public World(String pathToImage) throws Exception 
	{
		this.texture = new Texture(pathToImage);
		this.program = new ShaderProgram("world/world");
		this.program.createUniform("sampler");
		this.model = StaticModel.basicModel;
	}
	
	@Override
	public void render() 
	{
		if (loaded) {
			program.bind();
			
			program.setUniform("sampler", 0);
			
			texture.bind(0);
			model.render();
			program.unbind();
		}
	}

	public void cleanUp() 
	{
		this.model.cleanup();
		this.texture.cleanup();
		this.program.cleanup();
	}

	
	
}

package cz.vesely.game.client.render.renderers;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;

import cz.vesely.game.client.GameLogic;
import cz.vesely.game.client.GameState;
import cz.vesely.game.client.entity.GameObject;
import cz.vesely.game.client.render.ShaderProgram;
import cz.vesely.game.client.render.Window;

public class GameRenderer {

	private GuiRenderer guiRender;
	private TextRenderer textRenderer;
	
	public void init() throws Exception
	{
		guiRender = new GuiRenderer();
		textRenderer = new TextRenderer();
		textRenderer.init();
	}
	
	public void render(Window window, GameLogic logic)
	{
		clear();
		
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			logic.getCamera().calculateProjection(window);
			window.setResized(false);
		}
			
		Matrix4f projection = logic.getCamera().getProjection();
		
		{
			textRenderer.setProjection(projection);
			textRenderer.drawText("Test ?@-<>-", 0.5f, 0.5f);
		}
		
		{
			Matrix4f posAndScale = logic.getSelfPlayer().calculatePositionAndScale();
			
			ShaderProgram program = logic.getSelfPlayer().getProgram();
			
			program.bind();
			program.setUniform("projection", projection);
			program.setUniform("position", posAndScale);
			program.setUniform("sampler", 0);
			
			logic.getSelfPlayer().getTexture().bind(0);
			logic.getSelfPlayer().render();
			
			program.unbind();	
		}
		
		for (GameObject o : logic.getObjects())
		{
			Matrix4f posAndScale = o.calculatePositionAndScale();
			
			ShaderProgram program = o.getProgram();
			
			program.bind();
			program.setUniform("projection", projection);
			program.setUniform("position", posAndScale);
			program.setUniform("sampler", 0);
			
			o.getTexture().bind(0);
			o.render();
			
			program.unbind();
		}	
	}
	
	public void clear()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void cleanUp()
	{
		textRenderer.cleanup();
	}
	
}

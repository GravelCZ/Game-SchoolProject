package cz.vesely.game.client.render.renderers;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import cz.vesely.game.client.GameLogic;
import cz.vesely.game.client.GameState;
import cz.vesely.game.client.entity.GameObject;
import cz.vesely.game.client.gui.AbstractGui;
import cz.vesely.game.client.render.ShaderProgram;
import cz.vesely.game.client.render.Window;
import cz.vesely.game.client.render.tesselator.EnumRenderType;
import cz.vesely.game.client.render.tesselator.Tesselator;
import cz.vesely.game.client.render.tesselator.TesselatorRenderer;

public class GameRenderer {

	private GuiRenderer guiRender;
	private TextRenderer textRenderer;
	
	public void init(Window window, GameLogic logic) throws Exception
	{
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			logic.getCamera().calculateProjection(window);
			window.setResized(false);
		}
		
		guiRender = new GuiRenderer();
		textRenderer = new TextRenderer();
		textRenderer.init();
		
		Tesselator.createNewInstance();
	}
	
	public void render(Window window, GameLogic logic)
	{
		clear();
		
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			logic.getCamera().calculateProjection(window);
			window.setResized(false);
		}
		
		textRenderer.setProjection(logic.getCamera().getProjection());
		
		if (logic.getState() == GameState.LOGIN_MENU) {
			//this.textRenderer.setProjection(logic.getCamera().getProjection());
			
			renderLoginMenu(logic);
		} else {
			logic.getWorld().render();
			renderGame(logic);
		}
	}
	
	private void renderGame(GameLogic logic) 
	{	
		Matrix4f projection = logic.getCamera().getProjection();
		
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
	
		textRenderer.setScale(0.1f);
		textRenderer.drawText("a", 0.5f, 0.5f);
		
		Tesselator tess = Tesselator.getInstance();
		TesselatorRenderer tr = tess.getRenderer();
		
		tr.begin(GL11.GL_TRIANGLE_FAN, EnumRenderType.POS_COLOR, projection);
		
		tr.scale(4f);
		tr.translate(1, 0, 0);
		
		tr.posColor(0, 0, 0f,     1f, 0f, 0f, 1f);
		tr.posColor(1f, 0f, 0f,   0f, 1f, 0f, 0.5f);
		tr.posColor(1f, 1f, 0f,   0f, 0f, 1f, 0.25f);
		tr.posColor(0f, 1f, 0f,   1f, 1f, 1f, 0f);
		
		tess.draw();
	}

	private void renderLoginMenu(GameLogic logic) 
	{
		guiRender.renderGui(textRenderer, logic);
	}

	public void setGUI(AbstractGui gui)
	{
		this.guiRender.setGui(gui);
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

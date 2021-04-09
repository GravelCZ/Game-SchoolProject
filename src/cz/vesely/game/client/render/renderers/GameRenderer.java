package cz.vesely.game.client.render.renderers;

import static org.lwjgl.opengl.GL11.*;

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

	public GameRenderer() {
		guiRender = new GuiRenderer();
		textRenderer = new TextRenderer();
	}

	public void init(Window window, GameLogic logic) throws Exception {
		checkWindowResized(window, logic);
		textRenderer.init();

		Tesselator.createNewInstance();
	}

	public void render(Window window, GameLogic logic) {
		clear();

		checkWindowResized(window, logic);

		Matrix4f projection = logic.getCamera().getProjection();

		textRenderer.setProjection(projection);

		if (logic.getState() == GameState.MENU) {
			guiRender.renderGui(textRenderer, logic);
		} else {
			logic.getWorld().render(projection);
			renderGame(logic);
		}
	}

	private void renderGame(GameLogic logic) {
		Matrix4f projection = logic.getCamera().getProjection();

		for (GameObject o : logic.getObjects()) {
			Matrix4f posAndScale = o.calculatePositionAndScale();

			ShaderProgram program = o.getProgram();

			program.bind();
			program.setUniform("position", posAndScale);
			program.setUniform("sampler", 0);

			o.getTexture().bind(0);
			o.render(projection);

			program.unbind();
		}

		
		{
			Matrix4f posAndScale = logic.getSelfPlayer().calculatePositionAndScale();

			ShaderProgram program = logic.getSelfPlayer().getProgram();

			program.bind();
			program.setUniform("position", posAndScale);
			program.setUniform("sampler", 0);

			logic.getSelfPlayer().getTexture().bind(0);
			logic.getSelfPlayer().render(projection);

			program.unbind();
		}

		textRenderer.setScale(0.05f);
		textRenderer.drawText(logic.getSelfPlayer().getPosition().x + "," + logic.getSelfPlayer().getPosition().y, 0f, 0f);
		
		Tesselator tess = Tesselator.getInstance();
		TesselatorRenderer tr = tess.getRenderer();

		tr.begin(GL11.GL_TRIANGLE_FAN, EnumRenderType.POS_COLOR, projection);

		tr.scale(4f);
		tr.translate(0.5, 0.5, 0);

		tr.posColor(0, 0, 0f, 1f, 0f, 0f, 1f);
		tr.posColor(1f, 0f, 0f, 0f, 1f, 0f, 0.5f);
		tr.posColor(1f, 1f, 0f, 0f, 0f, 1f, 0.25f);
		tr.posColor(0f, 1f, 0f, 1f, 1f, 1f, 0f);
		
		tess.draw();
	}

	private void checkWindowResized(Window window, GameLogic logic) {
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			logic.getCamera().calculateProjection(window);
			window.setResized(false);
		}
	}

	private void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}

	public void setGUI(AbstractGui gui) {
		this.guiRender.setGui(gui);
	}

	public GuiRenderer getGuiRender() {
		return guiRender;
	}

	public void cleanUp() {
		textRenderer.cleanup();
	}

}

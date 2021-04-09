package cz.vesely.game.client.gui;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import cz.vesely.game.client.render.tesselator.EnumRenderType;
import cz.vesely.game.client.render.tesselator.Tesselator;
import cz.vesely.game.client.render.tesselator.TesselatorRenderer;

public class InputGui {

	private boolean isFocused = false;

	private String value = "";

	private int x;
	private int y;
	private int width;
	private int height;

	public InputGui(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void render(Matrix4f projection) {
		Tesselator t = Tesselator.getInstance();
		TesselatorRenderer tr = t.getRenderer();

		tr.begin(GL11.GL_TRIANGLE_FAN, EnumRenderType.POS_COLOR, new Matrix4f().identity());

		float c = 0.5f;
		float a = 1.0f;
		// tr.translate(-windowWidth/2, -windowHeight/2, 0);
		tr.translate(-0.5f, -0.5f, 0);
		// tr.scale(0.001f);

		tr.posColor(x, y, 0, c, c, c, a);
		tr.posColor(x + width, y, 0, c, c, c, a);
		tr.posColor(x + width, -y + height, 0, c, c, c, a);
		tr.posColor(x, -y + height, 0, c, c, c, a);

		tr.end();
	}

	public boolean isClickInside(int x, int y) {
		if (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height) {
			return true;
		}
		return false;
	}
}

package cz.vesely.game.client.render.renderers;

import cz.vesely.game.client.GameLogic;
import cz.vesely.game.client.gui.AbstractGui;

public class GuiRenderer {

	private AbstractGui gui;

	public void renderGui(TextRenderer textRender, GameLogic logic) {
		gui.render(textRender, logic);
	}

	public void setGui(AbstractGui gui) {
		this.gui = gui;
	}

	public void input(int x, int y) {
		gui.inputMouse(x, y);
	}

}

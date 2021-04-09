package cz.vesely.game.client.gui;

import cz.vesely.game.client.GameLogic;
import cz.vesely.game.client.render.renderers.TextRenderer;

public abstract class AbstractGui {

	public abstract void render(TextRenderer render, GameLogic logic);

	public abstract void inputKey(char c);

	public abstract void inputMouse(int x, int y);

}

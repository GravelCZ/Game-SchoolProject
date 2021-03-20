package cz.vesely.game.client.gui;

import cz.vesely.game.client.GameLogic;
import cz.vesely.game.client.render.IRenderable;
import cz.vesely.game.client.render.renderers.TextRenderer;

public abstract class AbstractGui {

	public abstract void render(TextRenderer render, GameLogic logic);
	
}

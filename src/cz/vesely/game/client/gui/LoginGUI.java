package cz.vesely.game.client.gui;

import cz.vesely.game.client.GameLogic;
import cz.vesely.game.client.render.renderers.TextRenderer;

public class LoginGUI extends AbstractGui {

	@Override
	public void render(TextRenderer render, GameLogic logic) 
	{
		render.setScale(50f);
		render.drawText("a", 0.5f, 0.5f);
	}

}

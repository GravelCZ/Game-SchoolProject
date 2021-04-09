package cz.vesely.game.client.gui;

import cz.vesely.game.client.GameLogic;
import cz.vesely.game.client.render.renderers.TextRenderer;

public class LoginGUI extends AbstractGui {

	private InputGui name;
	private InputGui ip;
	private InputGui port;

	public LoginGUI() {
		this.name = new InputGui(0, 0, 200, 200);
	}

	@Override
	public void render(TextRenderer render, GameLogic logic) {
		this.name.render(logic.getCamera().getProjection());
	}

	@Override
	public void inputKey(char c) {
		if (c == -1) {

		}
		System.out.println("Got key: " + c);
	}

	@Override
	public void inputMouse(int x, int y) {
		System.out.println(name.isClickInside(x, y));
	}

}

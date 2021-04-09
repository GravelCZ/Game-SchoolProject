package cz.vesely.game.client;

import cz.vesely.game.client.render.Window;
import cz.vesely.game.common.Timer;

public class Client {

	private Window window;
	private GameLogic logic;
	private Timer timer;
	private MouseInput mouseInput;

	public static final int FPS = 60;
	public static final int UPS = 60;

	public Client() {
		this.window = new Window("Hra", 1280, 720);
		this.timer = new Timer();
		this.mouseInput = new MouseInput();
		this.logic = new GameLogic(this.window);
	}

	public void run() {
		try {
			init();
			loop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loop() {
		float accumulator = 0f;
		float interval = 1f / UPS;

		while (!window.windowShouldClose()) {
			float elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;

			mouseInput.input(window);
			logic.input(mouseInput);

			while (accumulator >= interval) {
				logic.update(interval);
				accumulator -= interval;
			}

			logic.render();
			window.update();

			float loopSlot = 1F / FPS;
			double end = timer.getLastLoopTime() + loopSlot;
			while (timer.getTime() < end) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}
		}

		cleanup();
	}

	private void cleanup() {
		this.logic.cleanUp();
	}

	private void init() throws Exception {
		window.init();
		timer.init();
		mouseInput.init(window);
		logic.init();
	}
}

package cz.vesely.game.server;

import cz.vesely.game.common.Timer;

public class Server implements Runnable {
	
	private ServerGameLogic logic;
	private Timer timer;
	
	private boolean shouldShutdown = false;
	
	public static final int UPS = 60;
	
	public Server() {
		this.logic = new ServerGameLogic();
		this.timer = new Timer();
	}
	
	public void run()
	{
		try {
			init();
			loop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		timer.init();
		logic.init();
	}
	
	private void loop() 
	{
		float accumulator = 0f;
		float interval = 1f / UPS;
		
		while (!shouldShutdown) 
		{
			float deltaTime = timer.getElapsedTime();
			accumulator += deltaTime;
			
			while (accumulator >= interval) {
				logic.update(interval);
				accumulator -= interval;
			}
			
			double end = timer.getLastLoopTime() + interval;
			while (timer.getTime() < end) {
				try {
					Thread.sleep(1);
				} catch (Exception e) {}
			}
		}
		
		shutdown();
	}

	private void shutdown() {
		
	}
}

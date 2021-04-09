package cz.vesely.game.client.render.tesselator;

public class Tesselator {

	private TesselatorRenderer renderer;

	private static Tesselator instance;

	public Tesselator() throws Exception {
		renderer = new TesselatorRenderer();
	}

	public static Tesselator getInstance() {
		return instance;
	}

	public static void createNewInstance() throws Exception {
		if (instance == null) {
			instance = new Tesselator();
		}
	}

	public void draw() {
		renderer.end();
	}

	public TesselatorRenderer getRenderer() {
		return renderer;
	}

}

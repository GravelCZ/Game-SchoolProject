package cz.vesely.game.client.render;

import org.joml.Matrix4f;

@FunctionalInterface
public interface IRenderable {

	void render(Matrix4f projection);

}

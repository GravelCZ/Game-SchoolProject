package cz.vesely.game.client.render;

public class StaticModel {

	public static Model basicModel, playerModel;

	static {
		// ano, vím že toto je na prd ale nechce dělat načítač modelů
		{
			float[] verticies = new float[] { -1f, 1f, 0, // horní levá 0
					1f, 1f, 0, // horní pravá 1
					1f, -1f, 0, // dolní pravá 2
					-1f, -1f, 0, // dolní levá 3
			};
			int[] indicies = new int[] { 0, 1, 2, 2, 3, 0 };
			float[] textures = new float[] { 0, 0, 1, 0, 1, 1, 0, 1 };
			basicModel = new Model(verticies, textures, indicies);
		}
		{
			float[] verts = new float[] { -1.243f, 2.5f, 0, 1.243f, 2.5f, 0, 1.243f, -2.5f, 0, -1.243f, -2.5f, 0, };
			int[] indicies = new int[] { 0, 1, 2, 2, 3, 0 };
			float[] textures = new float[] { 0, 0, 1, 0, 1, 1, 0, 1 };
			playerModel = new Model(verts, textures, indicies);
		}
	}

}

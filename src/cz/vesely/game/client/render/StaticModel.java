package cz.vesely.game.client.render;

public class StaticModel {

	public static Model basicModel;
	
	static {
		
		float[] verticies = new float[]
		{
			-0.5f,  0.5f, 0, // horní levá   0
			 0.5f,  0.5f, 0, // horní pravá  1
			 0.5f, -0.5f, 0, // dolní pravá  2
			-0.5f, -0.5f, 0, // dolní levá   3
		};
		int[] indicies = new int[]
		{
			0,1,2,
			2,3,0
		};
		float[] textures = new float[]
		{
			0,0,
			1,0,
			1,1,
			0,1
		};
		basicModel = new Model(verticies, textures, indicies);
	}
	
}

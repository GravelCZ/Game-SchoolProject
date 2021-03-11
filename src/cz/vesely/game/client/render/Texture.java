package cz.vesely.game.client.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture 
{

	private int id;
	private int width, height;
	
	public Texture(int width, int height, ByteBuffer pixels) 
	{
		this.width = width;
		this.height = height;
		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
	}
	
	public Texture(String filename) throws IOException 
	{
		BufferedImage img = ImageIO.read(new File(filename));
		width = img.getWidth();
		height = img.getHeight();
		
		final int size = width * height * 4; // 4 * protože R G B a Alpha
		int[] pixels_raw = new int[size];
		pixels_raw = img.getRGB(0, 0, width, height, null, 0, width);
		
		ByteBuffer pixels = BufferUtils.createByteBuffer(size);
		
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				int pixel = pixels_raw[x * height + y];
				
				pixels.put((byte) ((pixel >> 16) & 0xFF)); // red
				pixels.put((byte) ((pixel >> 8) & 0xFF)); // green
				pixels.put((byte) ((pixel >> 0) & 0xFF)); // blue
				pixels.put((byte) ((pixel >> 24) & 0xFF)); // alpha
			}
		}
		
		pixels.flip();
		
		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		MemoryUtil.memFree(pixels);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void bind(int sample)
	{
		glActiveTexture(GL_TEXTURE0 + sample);
		glBindTexture(GL_TEXTURE_2D, id);	
	}

	public void cleanup() 
	{
		glDeleteTextures(id);
	}
}

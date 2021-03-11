package cz.vesely.game.client.render.renderers.text;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.system.MemoryUtil;

import cz.vesely.game.client.render.Texture;

public class Font {

	private Map<Character, Glyph> glyphs;

	private final Texture texture;

	private int fontHeight;

	public Font(java.awt.Font font) {
		glyphs = new HashMap<>();
		texture = createFontTexture(font);
	}

	private Texture createFontTexture(java.awt.Font font) {
		int imageWidth = 0;
		int imageHeight = 0;

		int x = 0;

		for (int i = 32; i < 256; i++) {
			if (i == 124) {
				continue;
			}

			char c = (char) i;
			BufferedImage img = renderChar(font, c);

			if (img == null) {
				continue;
			}

			imageWidth += img.getWidth();
			imageHeight = Math.max(imageHeight, img.getHeight());
		}

		this.fontHeight = imageHeight;

		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		
		for (int i = 32; i < 256; i++) {
			if (i == 127) {
				continue;
			}
			char c = (char) i;
			BufferedImage charImg = renderChar(font, c);
			if (charImg == null) {
				continue;
			}
			
			int charWidth = charImg.getWidth();
			int charHeight = charImg.getHeight();

			Glyph gl = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight);
			
			g.drawImage(charImg, x, 0, null);
			
			x += gl.w;
			glyphs.put(c, gl);
		}
		
		// Otočí texturu tak, aby měla začátek dole vlevo
		
		AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
		transform.translate(0, -image.getHeight()); 
		AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR); 
		image = operation.filter(image, null);
		
		int width = image.getWidth();
		int height = image.getHeight();

		/*try {
			File out = new File("./out.png");
			if (!out.exists()) {
				out.createNewFile();
			}
			ImageIO.write(image, "png", out);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		int[] pixels = new int[width * height];
		//image.setRGB(0, 0, width, height, pixels, 0, width); // zkopíruje obsah image do pixels		
		
		for (int tx = 0; tx < width; tx++)
		{
			for (int y = 0; y < height; y++)
			{
				pixels[y * width + tx] = image.getRGB(tx, y);
			}	
		}
		
		ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
		for (int y = 0; y < height; y++) {
			for (int tx = 0; tx < width; tx++) {
				int pixel = pixels[y * width + tx];
				
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		buffer.flip();

		Texture t = new Texture(width, height, buffer);
		MemoryUtil.memFree(buffer);

		return t;
	}

	private BufferedImage renderChar(java.awt.Font font, char c) {
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setFont(font);
		FontMetrics met = g.getFontMetrics();
		g.dispose();

		int charWidth = met.charWidth(c);
		int charHeight = met.getHeight();

		if (charWidth == 0) {
			return null;
		}

		image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
		g = image.createGraphics();
		g.setFont(font);
		g.setPaint(Color.BLACK);
		g.drawString(String.valueOf(c), 0, met.getAscent());
		g.dispose();

		return image;
	}

	public Texture getTexture() {
		return texture;
	}

	public int getFontHeight() {
		return fontHeight;
	}

	public Map<Character, Glyph> getGlyphs() {
		return this.glyphs;
	}

	public void cleanup() {
		this.texture.cleanup();
	}

}

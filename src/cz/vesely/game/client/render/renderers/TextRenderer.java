package cz.vesely.game.client.render.renderers;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.awt.Color;
import java.io.FileInputStream;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import cz.vesely.game.client.render.ShaderProgram;
import cz.vesely.game.client.render.renderers.text.Font;
import cz.vesely.game.client.render.renderers.text.Glyph;

public class TextRenderer {

	private Font f;

	private ShaderProgram p;

	private int numVert;
	private FloatBuffer verts;

	private int vaoId, vboId;

	private float scale = 1f;

	private Matrix4f projection;

	public void init() throws Exception {
		java.awt.Font f = java.awt.Font.createFont(0, new FileInputStream("./firacode.ttf"))
				.deriveFont(java.awt.Font.PLAIN, 16);
		this.f = new Font(f);
		p = new ShaderProgram("text/text");
		p.createUniform("sampler");
		p.createUniform("scale");
		p.createUniform("projection");

		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);

		vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);

		int posAttr = p.getAttrLoc("pos");
		glEnableVertexAttribArray(posAttr);
		glVertexAttribPointer(posAttr, 2, GL_FLOAT, false, 8 * Float.BYTES, 0);

		int colorAttr = p.getAttrLoc("color");
		glEnableVertexAttribArray(colorAttr);
		glVertexAttribPointer(colorAttr, 4, GL_FLOAT, false, 8 * Float.BYTES, 2 * Float.BYTES);

		int texAttr = p.getAttrLoc("texcoord");
		glEnableVertexAttribArray(texAttr);
		glVertexAttribPointer(texAttr, 2, GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);

		verts = MemoryUtil.memAllocFloat(4096);
		long size = verts.capacity() * Float.BYTES;
		glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		numVert = 0;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setProjection(Matrix4f projection) {
		this.projection = projection;
	}

	private void end() {
		verts.flip();

		f.getTexture().bind(0);

		p.bind();
		p.setUniform("sampler", 0);
		p.setUniform("scale", this.scale);
		p.setUniform("projection", this.projection);

		glBindVertexArray(vaoId);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferSubData(GL_ARRAY_BUFFER, 0, verts);

		glDrawArrays(GL_TRIANGLES, 0, numVert);

		p.unbind();

		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		verts.clear();
		numVert = 0;
	}

	public int getWidth(CharSequence text) {
		int width = 0;
		int lineWidth = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '\n') {
				width = Math.max(width, lineWidth);
				lineWidth = 0;
				continue;
			}

			Glyph g = f.getGlyphs().get(c);
			lineWidth += g.w;
		}
		width = Math.max(width, lineWidth);
		return width;
	}

	public int getHeight(CharSequence text) {
		int height = 0;
		int lineHeight = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '\n') {
				height += lineHeight;
				lineHeight = 0;
				continue;
			}
			if (c == '\r') {
				continue;
			}
			Glyph g = f.getGlyphs().get(c);
			lineHeight = Math.max(lineHeight, g.h);
		}
		height += lineHeight;
		return height;
	}

	public void drawText(String text, float x, float y) {
		int textHeight = getHeight(text);

		float drawX = x;
		float drawY = y;
		if (textHeight > f.getFontHeight()) {
			drawY += textHeight - f.getFontHeight();
		}

		this.numVert = 0;
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch == '\n') {
				drawY -= f.getFontHeight();
				drawX = x;
				continue;
			}
			if (ch == '\r') {
				continue;
			}
			Glyph g = f.getGlyphs().get(ch);

			drawCalculateRegion(drawX, drawY, g.x, g.y, g.w, g.h);
			drawX += g.w;
		}
		end();
	}

	private void drawCalculateRegion(float x, float y, float regX, float regY, float regW, float regH) {
		float x1 = x;
		float y1 = y;
		float x2 = x + regW;
		float y2 = y + regH;

		float s1 = regX / f.getTexture().getWidth();
		float t1 = regY / f.getTexture().getHeight();
		float s2 = (regX + regW) / f.getTexture().getWidth();
		float t2 = (regY + regH) / f.getTexture().getHeight();

		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, Color.WHITE);
	}

	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2,
			Color c) {
		if (verts.remaining() < 8 * 6) {
			end();
		}

		float r = c.getRed();
		float g = c.getGreen();
		float b = c.getBlue();
		float a = c.getAlpha();

		verts.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
		verts.put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2);
		verts.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);

		verts.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
		verts.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
		verts.put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1);

		numVert += 6;
	}

	public void cleanup() {
		MemoryUtil.memFree(verts);
		this.f.cleanup();
		this.p.cleanup();

		glDeleteVertexArrays(vaoId);
		glDeleteBuffers(vboId);
	}

}

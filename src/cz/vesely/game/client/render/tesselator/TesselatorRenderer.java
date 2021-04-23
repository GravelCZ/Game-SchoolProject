package cz.vesely.game.client.render.tesselator;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import cz.vesely.game.client.render.ShaderProgram;

public class TesselatorRenderer {

	private FloatBuffer posColorBuffer;
	private FloatBuffer posTexBuffer;

	private ShaderProgram programTexture, programColor;

	private EnumRenderType renderType;

	private int vertexCount;

	private boolean drawing;
	private int drawMode;

	private float scale = 1f;
	private double xOffset, yOffset, zOffset;

	private int textureVaoId, textureVboId;
	private int colorVaoId, colorVboId;

	private Matrix4f projection;

	public TesselatorRenderer() throws Exception {
		programColor = new ShaderProgram("tess/color");
		programColor.createUniform("scale");
		programColor.createUniform("projection");

		programTexture = new ShaderProgram("tess/texture");
		programTexture.createUniform("sampler");
		programTexture.createUniform("scale");
		programTexture.createUniform("projection");

		{
			textureVaoId = glGenVertexArrays();
			glBindVertexArray(textureVaoId);

			textureVboId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, textureVboId);

			int dataSize = (3 + 2) * Float.BYTES;

			int posAttr = programTexture.getAttrLoc("pos");
			glEnableVertexAttribArray(posAttr);
			glVertexAttribPointer(posAttr, 3, GL_FLOAT, false, dataSize, 0);

			int texAttr = programTexture.getAttrLoc("texcoord");
			glEnableVertexAttribArray(texAttr);
			glVertexAttribPointer(texAttr, 2, GL_FLOAT, false, dataSize, 2 * Float.BYTES);

			posTexBuffer = MemoryUtil.memAllocFloat(4096);
			long size = posTexBuffer.capacity() * Float.BYTES;
			glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}

		{
			colorVaoId = glGenVertexArrays();
			glBindVertexArray(colorVaoId);

			colorVboId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, colorVboId);

			int dataSize = (3 + 4) * Float.BYTES;

			int posAttr = programColor.getAttrLoc("pos");
			glEnableVertexAttribArray(posAttr);
			glVertexAttribPointer(posAttr, 3, GL_FLOAT, false, dataSize, 0);

			int colorAttr = programColor.getAttrLoc("color");
			glEnableVertexAttribArray(colorAttr);
			glVertexAttribPointer(colorAttr, 4, GL_FLOAT, false, dataSize, 3 * Float.BYTES);

			posColorBuffer = MemoryUtil.memAllocFloat(4096);
			long size = posColorBuffer.capacity() * Float.BYTES;
			glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}

		vertexCount = 0;
	}

	public void begin(int drawMode, EnumRenderType renderType, Matrix4f projection) {
		if (drawing) {
			throw new IllegalStateException("Already drawing");
		}
		this.projection = projection;
		this.drawMode = drawMode;
		this.renderType = renderType;

		reset();
	}

	public void end() {
		draw();
		reset();

		this.drawMode = -1;
		this.renderType = null;
		this.drawing = false;

		this.projection = null;
	}

	public void translate(double x, double y, double z) {
		this.xOffset = x;
		this.yOffset = y;
		this.zOffset = z;
	}

	public TesselatorRenderer posTex(float x, float y, float z, float u, float v) {
		if (this.renderType != EnumRenderType.POS_TEXTURE) {
			throw new IllegalStateException("Cannot use texture whil drawing color");
		}

		if (posTexBuffer.remaining() < 5) {
			draw();
		}

		posTexBuffer.put((float) (x + this.xOffset)).put((float) (y + this.yOffset)).put((float) (z + this.zOffset))
				.put(u).put(v);

		vertexCount++;
		return this;
	}

	public TesselatorRenderer posColor(float x, float y, float z, float r, float g, float b, float a) {
		if (this.renderType != EnumRenderType.POS_COLOR) {
			throw new IllegalStateException("Cannot use color while drawing texture");
		}

		if (posColorBuffer.remaining() < 7) {
			draw();
		}

		posColorBuffer
				.put((float) (x + this.xOffset))
				.put((float) (y + this.yOffset))
				.put((float) (z + this.zOffset))
				.put(r).put(g).put(b).put(a);

		vertexCount++;

		return this;
	}

	public void scale(float scale) {
		this.scale = scale;
	}

	private void draw() {
		posColorBuffer.flip();
		posTexBuffer.flip();

		if (this.renderType == EnumRenderType.POS_COLOR) {

			programColor.bind();
			programColor.setUniform("scale", this.scale);
			programColor.setUniform("projection", this.projection);

			glBindVertexArray(colorVaoId);
			glBindBuffer(GL_ARRAY_BUFFER, colorVboId);
			glBufferSubData(GL_ARRAY_BUFFER, 0, posColorBuffer);

			glDrawArrays(this.drawMode, 0, vertexCount);

			programColor.unbind();

		} else if (this.renderType == EnumRenderType.POS_TEXTURE) {

			programTexture.bind();
			programTexture.setUniform("scale", this.scale);
			programTexture.setUniform("projection", this.projection);

			glBindVertexArray(textureVaoId);
			glBindBuffer(GL_ARRAY_BUFFER, textureVboId);
			glBufferSubData(GL_ARRAY_BUFFER, 0, posTexBuffer);

			glDrawArrays(this.drawMode, 0, vertexCount);

			programTexture.unbind();
		}

		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		this.posColorBuffer.clear();
		this.posTexBuffer.clear();

		vertexCount = 0;
	}

	private void reset() {
		posColorBuffer.clear();
		posTexBuffer.clear();

		xOffset = 0;
		yOffset = 0;
		zOffset = 0;

		scale = 1f;
		vertexCount = 0;
	}

	public void cleanup() {
		MemoryUtil.memFree(posColorBuffer);
		MemoryUtil.memFree(posTexBuffer);

		this.programColor.cleanup();
		this.programTexture.cleanup();

		glDeleteVertexArrays(colorVaoId);
		glDeleteVertexArrays(textureVaoId);

		glDeleteBuffers(colorVboId);
		glDeleteBuffers(textureVboId);
	}
}

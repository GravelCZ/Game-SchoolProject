package cz.vesely.game.client.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryUtil;

public class Model {

	private int vaoId;

	private int vertexCount;

	private List<Integer> vboIdList;

	public Model(float[] positions, float[] texCoords, int[] indicies) {
		FloatBuffer posBuffer = null;
		FloatBuffer texBuffer = null;
		IntBuffer indiciesBuffer = null;
		try {
			this.vertexCount = indicies.length;
			vboIdList = new ArrayList<>();

			vaoId = glGenVertexArrays();
			glBindVertexArray(vaoId);

			int vboId = glGenBuffers();
			vboIdList.add(vboId);
			posBuffer = MemoryUtil.memAllocFloat(positions.length);
			posBuffer.put(positions).flip();
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

			vboId = glGenBuffers();
			vboIdList.add(vboId);
			texBuffer = MemoryUtil.memAllocFloat(texCoords.length);
			texBuffer.put(texCoords).flip();
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, texBuffer, GL_STATIC_DRAW);
			glEnableVertexAttribArray(1);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

			vboId = glGenBuffers();
			vboIdList.add(vboId);
			indiciesBuffer = MemoryUtil.memAllocInt(indicies.length);
			indiciesBuffer.put(indicies).flip();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL_STATIC_DRAW);

			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);

		} finally {
			if (posBuffer != null) {
				MemoryUtil.memFree(posBuffer);
			}
			if (texBuffer != null) {
				MemoryUtil.memFree(texBuffer);
			}
			if (indiciesBuffer != null) {
				MemoryUtil.memFree(indiciesBuffer);
			}
		}
	}

	public void render() {
		glBindVertexArray(vaoId);
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

		glBindVertexArray(0);
	}

	public void cleanup() {
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		vboIdList.stream().forEach(i -> glDeleteBuffers(i));

		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}
}

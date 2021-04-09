package cz.vesely.game.client.render;

import static org.lwjgl.opengl.GL20.*;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram {

	private int program;

	private Map<String, Integer> uniforms;

	public ShaderProgram(String filename) throws Exception {
		uniforms = new HashMap<>();

		program = glCreateProgram();

		if (program == 0) {
			throw new RuntimeException("Could not create shader program");
		}

		File folder = new File("./shaders/");
		if (!folder.exists()) {
			throw new IOException("Shaders folder not found!");
		}

		File vsFile = new File(folder, filename + ".vs");
		File fsFile = new File(folder, filename + ".fs");

		createShader(new String(Files.readAllBytes(vsFile.toPath())), GL_VERTEX_SHADER);
		createShader(new String(Files.readAllBytes(fsFile.toPath())), GL_FRAGMENT_SHADER);

		link();
	}

	public void createUniform(String name) {
		int uniformLocation = glGetUniformLocation(program, name);

		if (uniformLocation < 0) {
			throw new RuntimeException("Could not find uniform " + name + " for shader program " + program);
		}

		uniforms.put(name, uniformLocation);
	}

	public void setUniform(String name, int value) {
		glUniform1i(uniforms.get(name), value);
	}

	public void setUniform(String name, float f) {
		glUniform1f(uniforms.get(name), f);
	}

	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
	}

	public void setUniform(String uniformName, Vector4f value) {
		glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
	}

	public void setUniform(String name, Matrix4f matrix4f) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			matrix4f.get(fb);
			glUniformMatrix4fv(uniforms.get(name), false, fb);
		}
	}

	public int getAttrLoc(String name) {
		return glGetAttribLocation(program, name);
	}

	private int createShader(String src, int type) throws Exception {
		int shaderId = glCreateShader(type);
		if (shaderId == 0) {
			throw new Exception("Could not create shader " + type);
		}

		glShaderSource(shaderId, src);
		glCompileShader(shaderId);

		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling shader code: " + glGetShaderInfoLog(shaderId, 1024));
		}

		glAttachShader(program, shaderId);

		return shaderId;
	}

	private void link() throws Exception {
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program));
			throw new RuntimeException("Failed to link shader program");
		}
		glValidateProgram(program);
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			throw new RuntimeException("Failed to validate shader program");
		}
	}

	public void bind() {
		glUseProgram(program);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public void cleanup() {
		unbind();
		if (program != 0) {
			glDeleteProgram(program);
		}
	}
}

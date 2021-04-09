package cz.vesely.game.client.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.function.Consumer;

import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window implements GLFWCharCallbackI {

	private String title;

	private int width, height;

	private long windowHandle;
	private boolean resized = true;

	private Consumer<Integer> inputCallback;

	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	public void init() {
		GLFWErrorCallback.createPrint(System.out).set();

		if (!glfwInit()) {
			return;
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_SAMPLES, 4);

		windowHandle = glfwCreateWindow(width, height, title, 0, 0);

		if (windowHandle == NULL) {
			throw new RuntimeException("Failed to create window");
		}

		glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
			this.width = width;
			this.height = height;
			this.resized = true;
		});

		glfwSetKeyCallback(windowHandle, (window, key, code, action, mod) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true);
			}
			if (key == GLFW_KEY_ENTER && action == GLFW_RELEASE) {
				if (glfwGetInputMode(windowHandle, GLFW_CURSOR) == GLFW_CURSOR_DISABLED) {
					glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
				} else {
					glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
				}
			}

			if (mod == GLFW_MOD_SHIFT) {
				if (key == GLFW_KEY_P) {
					glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
				} else if (key == GLFW_KEY_O) {
					glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
				}

				if (key == GLFW_KEY_J) {
					glEnable(GL_MULTISAMPLE);
				} else if (key == GLFW_KEY_K) {
					glDisable(GL_MULTISAMPLE);
				}
			}
		});

		glfwSetCharCallback(windowHandle, this);

		glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		glfwMakeContextCurrent(windowHandle);

		glfwShowWindow(windowHandle);

		GL.createCapabilities();

		setClearColor(1f, 1f, 1f, 0f);

		// glEnable(GL_CULL_FACE);
		// glEnable(GL_FRONT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void invoke(long window, int codepoint) {
		inputCallback.accept(codepoint);
	}

	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}

	public boolean isKeyPressed(int code) {
		return glfwGetKey(this.windowHandle, code) == GLFW_PRESS;
	}

	public void setClearColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isResized() {
		return resized;
	}

	public boolean windowShouldClose() {
		return glfwWindowShouldClose(this.windowHandle);
	}

	public long getWindowHandle() {
		return this.windowHandle;
	}

	public void setResized(boolean b) {
		this.resized = b;
	}

	public void setInputCallback(Consumer<Integer> toCall) {
		this.inputCallback = toCall;
	}
}

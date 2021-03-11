package cz.vesely.game.client;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2d;
import org.joml.Vector2f;

import cz.vesely.game.client.render.Window;

public class MouseInput {

	private Vector2d prevPos;
	private Vector2d currPos;
	
	private Vector2f displVec;
	
	private boolean inWindow = false;
	
	private boolean leftPressed = false, rightPressed = false;
	
	public MouseInput() 
	{
		prevPos = new Vector2d(-1, -1);
		currPos = new Vector2d(0, 0);
		displVec = new Vector2f();
	}
	
	public void init(Window window) 
	{
		glfwSetCursorPosCallback(window.getWindowHandle(), (handle, xpos, ypos) -> {
			currPos.x = xpos;
			currPos.y = ypos;
		});
		glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            inWindow = entered;
        });
        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
        	leftPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
        	rightPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });	
	}

	public void input(Window window) {
		displVec.x = 0;
		displVec.y = 0;
		if (prevPos.x > 0 && prevPos.y > 0 && inWindow) {
			double dx = currPos.x - prevPos.x;
			double dy = currPos.y - prevPos.y;
			
			if (dx != 0) {
				displVec.y = (float) dx;
			}
			if (dy != 0) {
				displVec.x = (float) dy; 
			}
		}
		
		prevPos.x = currPos.x;
		prevPos.y = currPos.y;
	}
	
	public boolean leftPressed() {
		return this.leftPressed;
	}
	
	public boolean rightPressed() {
		return this.rightPressed;
	}
	
	public Vector2d getCurrPos() {
		return currPos;
	}
	
	public Vector2f getDisplVec() {
		return this.displVec;
	}

}

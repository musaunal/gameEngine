package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0,10,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public void move(){
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			position.z -=0.2f;
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			position.x +=0.2f;
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			position.x -=0.2f;
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			position.z +=0.2f;
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			position.y +=0.2f;
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
			position.y -=0.2f;
	}
	
	public Camera(){
		
	}

	public Vector3f getPositon() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	
	
}

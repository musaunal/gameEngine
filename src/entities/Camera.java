package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0,10,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public void move(){
		if (Keyboard.isKeyDown(Keyboard.KEY_8))
			position.z -=2;
		if (Keyboard.isKeyDown(Keyboard.KEY_6))
			position.x +=2;
		if (Keyboard.isKeyDown(Keyboard.KEY_4))
			position.x -=2;
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			position.z +=2;
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			position.y +=2;
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
			position.y -=2;
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

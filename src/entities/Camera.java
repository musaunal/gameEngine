package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private float distanceFromPlayer = 20;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 30;    // kamera a��s�
	private float yaw = 0;
	private float roll;
	
	private Player player;

	public Camera(Player player){
		this.player = player;
	}

	public void move(){
		
		calculateAngleAroundPlayer();
		calculateZoom();
		calculatePitch();
		float horizontalDistance = calculateHorizontalDistance();
		float VerticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, VerticalDistance);
		
		/*if (Keyboard.isKeyDown(Keyboard.KEY_8))
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
			position.y -=2;*/
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
	
	private void calculateCameraPosition(float horizDistance, float verticDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ ;
		position.y = player.getPosition().y +15;
		this.yaw = 180 - (theta	);
	}
	
	private float calculateHorizontalDistance(){
		return  (float) (distanceFromPlayer * Math.tan(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance(){
		return  (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() *0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0)){
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
}

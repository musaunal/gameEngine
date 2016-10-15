package entities;

import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import engineTester.MainGameLoop;
import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

	private static final float RUN_SPEED = 40;
	private static final float TURN_SPEED = 160;
	public static final float GRAVITY = -50;
	private static final float JUMP_POWER = 90;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;
	private boolean isMoving = false;
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move (Terrain terrain){
		
		if(isMoving){
			/** SOCKET *
			
			try {								//arrange your cpu usage
			    Thread.sleep(8);                 //1000 milliseconds is one second. 
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
			JSONObject pos = new JSONObject();

			try {
				pos.put("x", this.getPosition().x);
				pos.put("y", this.getPosition().y);
				pos.put("z", this.getPosition().z);
				pos.put("id", MainGameLoop.a);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			MainGameLoop.GSocket.socket.emit("position", pos);
			** END SOCKET **/
		}
		
		
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(	dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
		if(super.getPosition().y < terrainHeight){
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump (){
		if(!isInAir){
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}
	
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentSpeed = RUN_SPEED;
			isMoving = true;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed = - RUN_SPEED;
			isMoving = true;
		}else {
			this.currentSpeed = 0;
			isMoving = false;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentTurnSpeed = - TURN_SPEED;
			isMoving = true;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentTurnSpeed = TURN_SPEED;
			isMoving = true;
		}else{
			this.currentTurnSpeed = 0;
			isMoving = false;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jump();
		}
	}
}

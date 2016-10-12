package multi;

import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.util.vector.Vector3f;

import engineTester.MainGameLoop;
import entities.Player;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
public class GameSocket implements IOCallback {
	
	public SocketIO socket ;
	

	public GameSocket(String address) throws Exception{
		
		socket = new SocketIO();
		socket.connect(address, this);
		
	}

	@Override
	public void onError(SocketIOException socketIOException) {
		System.out.println("an Error occured");
		socketIOException.printStackTrace();
	}

	@Override
	public void onDisconnect() {
		System.out.println("Connection terminated.");
	}

	@Override
	public void onConnect() {
		System.out.println("Connection established");
	}

	@Override    //Serverdan gelen event burada iþlenir
	public void on(String event, IOAcknowledge ack, Object... args) {
		//System.out.println("Server triggered event '" + event + "'");
		String ev = "setpos";
		if(event.equals(ev)){
			JSONObject json = (JSONObject)args[0];
			try {
				if(MainGameLoop.a != json.getInt("id"))
					MainGameLoop.entityLamp.setPosition(new Vector3f(json.getInt("x"),json.getInt("y"), json.getInt("z")));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void onMessage(JSONObject arg0, IOAcknowledge arg1) {
		System.out.println("Server said: " + arg0);
		
	}

	@Override
	public void onMessage(String arg0, IOAcknowledge arg1) {
		System.out.println("Server said: " + arg0);
		
	}
}

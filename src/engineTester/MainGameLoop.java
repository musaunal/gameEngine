package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		float[] vertices = { 
				-0.5f, 0.5f, 0,   // V0
				-0.5f, -0.5f, 0,  // V1
				0.5f, -0.5f, 0,   // V2
				0.5f, 0.5f, 0f,	  // V3
		};
		
		int[] indices = {
				0,1,3, //  top left
				3,1,2, //   righ bottom
		};

		
		RawModel model = loader.loadToVAO(vertices , indices);
		
		while (!Display.isCloseRequested()){
			renderer.prepare();
			//game logic 
			shader.start();
			renderer.render(model);
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUP();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

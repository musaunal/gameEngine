package engineTester;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiTexture;
import guis.guiRenderer;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolBox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();	
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy")); 
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt")); 
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers")); 
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path")); 
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap")); 
		/*
		RawModel model = OBJLoader.loadObjModel("lowPolyTree", loader); 
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("lowPolyTree")));
		
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("flower")));
		TexturedModel pine = new TexturedModel(OBJLoader.loadObjModel("pine", loader),
				new ModelTexture(loader.loadTexture("pine")));
	
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),fernTextureAtlas);
	
		
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		fern.getTexture().setHasTransparency(true);
		lamp.getTexture().setUseFakeLighting(true);
	*/	
		Terrain terrain = new Terrain(0, 0, loader , texturePack, blendMap , "heightMap");
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);
		
		/*
		Random random = new Random(676452);
		for(int i=0; i<400; i++){
			if(i % 20 == 0){
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(grass, new Vector3f(x , y, z),0,random.nextFloat() *360,0,0.9f));
				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(flower, new Vector3f(x, y, z),0,0,0,2.3f));
			}
			if (i % 5 == 0){
				float x = random.nextFloat() * 800 - 400 ;
				float z = random.nextFloat()* -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(staticModel, new Vector3f(x ,y , z),0,0,0,random.nextFloat() * 1 + 1));
				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x,y,z),0,random.nextFloat() * 360 ,0 ,0.9f));
				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(pine, new Vector3f(x,y ,z),0 ,random.nextFloat() * 360 ,0 ,random.nextFloat() * 0.5f + 3.8f));
			}
		
		}
		*/
		List<Light> lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(1.2f, 1.2f, 1.2f)));
		lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2,0,0), new Vector3f(1 ,0.01f , 0.002f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0,2,2), new Vector3f(1 ,0.01f , 0.002f)));
		lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2,2,0), new Vector3f(1 ,0.01f , 0.002f)));
		

		TexturedModel lamp = new TexturedModel (OBJLoader.loadObjModel("lamp", loader),
				new ModelTexture(loader.loadTexture("lamp")));
		
		
		entities.add(new Entity(lamp, new Vector3f(0, -25 , 0), 0, 0, 0, 5));
	/*	entities.add(new Entity(lamp, new Vector3f(370, 4.2f , -300), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(293, -6.8f , -305), 0, 0, 0, 1));
		
		Terrain terrain2 = new Terrain(1, 0,loader, texturePack, blendMap , "heightMap");
	*/
		MasterRenderer renderer = new MasterRenderer(loader);
		
		RawModel Person = OBJLoader.loadObjModel("person", loader);
		TexturedModel person = new TexturedModel(Person, new ModelTexture(loader.loadTexture("playerTexture")));
		
		Player player = new Player(person, new Vector3f(50, 5 , 0), 0, 0, 0, 1);
		
		Camera camera = new Camera(player);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.80f, 0.90f), new Vector2f(0.2f,0.2f));
		guis.add(gui);
		
		guiRenderer guiRenderer = new guiRenderer(loader);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix() ,terrain);
		
//		Entity lampEntity = (new Entity(lamp, new Vector3f(293, 7 ,-305),0,0,0,1 ));
//		entities.add(lampEntity);
		
		WaterShader waterShader  = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix());
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(100, 100, 0);
		waters.add(water);
		
		WaterFrameBuffers fbos = new WaterFrameBuffers();
		GuiTexture refraction = new GuiTexture(fbos.getRefractionTexture(), new Vector2f(0.5f ,0.5f), new Vector2f(0.25f ,0.25f));
		GuiTexture reflection = new GuiTexture(fbos.getReflectionTexture(), new Vector2f(-0.5f ,0.5f), new Vector2f(0.25f ,0.25f));
		guis.add(refraction);
		guis.add(reflection);
		
		while (!Display.isCloseRequested()){
			player.move(terrain);
			camera.move();
			picker.update();

			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			fbos.bindReflectionFrameBuffer();
			float distance = 2* (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, terrains, lights, camera ,new Vector4f(0, 1, 0 , -water.getHeight()));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			fbos.bindRefractionFrameBuffer();
			renderer.renderScene(entities, terrains, lights, camera ,new Vector4f(0, -1, 0 , water.getHeight()));
			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			fbos.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, terrains, lights, camera,new Vector4f(0, 1, 0 ,10000));
			waterRenderer.render(waters, camera);
		
		/*	
			renderer.processEntity(player);
			renderer.processTerrrain(terrain);
			renderer.processTerrrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			
			renderer.render(lights, camera);
		*/	guiRenderer.render(guis);
			DisplayManager.updateDisplay();	
		}
		
		fbos.cleanUp();
		waterShader.cleanUP();
		guiRenderer.cleanUP();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

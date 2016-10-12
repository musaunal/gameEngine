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
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import multi.GameSocket;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
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

	public static GameSocket GSocket;
	public static Player player;
	public static Entity entityLamp ;
	public static int a = 1;
	
	
	public static void main(String[] args) {
		
		try {
			GSocket = new GameSocket("http://192.168.2.105:3000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		
		RawModel Person = OBJLoader.loadObjModel("person", loader);
		TexturedModel person = new TexturedModel(Person, new ModelTexture(loader.loadTexture("playerTexture")));
		
		player = new Player(person, new Vector3f(1000, 50, 250), 0, 0, 0, 0.6f);
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer(loader,camera);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		FontType font = new FontType(loader.loadTexture("candara"), "candara");
		GUIText text = new GUIText("WELCOME TO MY GAME", 2f, font, new Vector2f(0, 0.12f), 1f, true);
		text.setColour(0.1f, 0.1f, 0.1f);
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2")); 
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud")); 
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers")); 
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path")); 
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap")); 
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		
		TexturedModel fern = new TexturedModel(OBJFileLoader.loadOBJ("fern", loader),fernTextureAtlas);
		
		fern.getTexture().setHasTransparency(true);
		
		TexturedModel bobble = new TexturedModel(OBJFileLoader.loadOBJ("pine", loader),
				new ModelTexture(loader.loadTexture("pine")));
		bobble.getTexture().setHasTransparency(true);

		Terrain terrain = new Terrain(1, 0, loader , texturePack, blendMap , "heightMap");
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);
		
		RawModel lampmodel = OBJLoader.loadObjModel("person", loader);
		TexturedModel lamp = new TexturedModel(lampmodel, new ModelTexture(loader.loadTexture("playerTexture")));
		entityLamp = new Entity(lamp, new Vector3f(1000, 10, 230), 0, 0, 0, 0.6f);
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
	
		//NormalMap models
		
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader), 
				new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		
		TexturedModel crateModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("crate", loader),
				new ModelTexture(loader.loadTexture("crate")));
		crateModel.getTexture().setNormalMap(loader.loadTexture("crateNormal"));
		crateModel.getTexture().setShineDamper(10);
		crateModel.getTexture().setReflectivity(0.5f);
		
		TexturedModel boulderModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("boulder", loader),
				new ModelTexture(loader.loadTexture("boulder")));
		boulderModel.getTexture().setNormalMap(loader.loadTexture("boulderNormal"));
		boulderModel.getTexture().setShineDamper(10);
		boulderModel.getTexture().setReflectivity(0.5f);
	
		//Entites
		
		Entity entity = new Entity(barrelModel, new Vector3f(875, 40, 75), 0, 0, 0, 1f);
		Entity entity2 = new Entity(boulderModel, new Vector3f(885, 40, 75), 0, 0, 0, 1f);
		Entity entity3 = new Entity(crateModel, new Vector3f(865, 40, 75), 0, 0, 0, 0.04f);
		normalMapEntities.add(entity);
		normalMapEntities.add(entity2);
		normalMapEntities.add(entity3);
		
		Random random = new Random(5666778);
		for (int i = 0; i < 250; i++) {
			if (i % 3 == 0) {
				float x = random.nextFloat() * 1000 + 800;
				float z = random.nextFloat() * 1000;
				if ((x > 800 && x < 1600) && (z > 0 && z < 800)) {
				
					float y = terrain.getHeightOfTerrain(x, z);
					if(y>0){
						entities.add(new Entity(fern, 3, new Vector3f(x, y, z), 0,random.nextFloat() * 360, 0, 0.9f));
					}
					
				}
			}
			if (i % 2 == 0) {

				float x = random.nextFloat() * 1000 + 800;
				float z = random.nextFloat() * 1000;
				if ((x > 800 && x < 1600) && (z >0 && z < 800)) {

					float y = terrain.getHeightOfTerrain(x, z);
					if(y>0){
					entities.add(new Entity(bobble, 1, new Vector3f(x, y, z), 0,random.nextFloat() * 360, 0, random.nextFloat() * 0.6f + 0.8f));
					}
				}
			}
		}
		
		
		
		entities.add(entityLamp);
		entities.add(player);
		
		//other setup
		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);
	
		Light lamba = new Light(new Vector3f(1250, 60, 250), new Vector3f(1, 0, 0),new Vector3f(1, 0, 0));
		//lights.add(lamba);
		
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), new Vector2f(0.5f,0.5f),new Vector2f(0.5f,0.5f));
		//guis.add(shadowMap);  
		
		GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.80f, 0.90f), new Vector2f(0.2f,0.2f));
		guis.add(gui);
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix() ,terrain);
		
		// water set-up
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader  = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix() ,buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(1200, 400, 0);			// koordinatý suyun merkezine ait
		waters.add(water);
		
		
		ParticleTexture cosmic = new ParticleTexture(loader.loadTexture("cosmic"), 4, false);
		ParticleTexture fire = new ParticleTexture(loader.loadTexture("fire"), 8, true);
		
		ParticleSystem firee = new ParticleSystem(fire, 500, 0.9f, -0.03f, 4, 5);
		firee.setScaleError(0.5f);
		ParticleSystem systemo = new ParticleSystem(cosmic, 50, 5, 0.1f, 3, 0.6f);
		systemo.setLifeError(0.1f);
		systemo.setSpeedError(0.25f);
		systemo.setScaleError(0.5f);
		systemo.randomizeRotation();
		
		Fbo fbo = new Fbo(Display.getHeight(),Display.getHeight(),Fbo.DEPTH_RENDER_BUFFER);
		PostProcessing.init(loader);
		
		 //game loop
		
		while (!Display.isCloseRequested()){
			player.move(terrain);
			camera.move();
			picker.update();
			
			systemo.generateParticles(new Vector3f(player.getPosition().x, player.getPosition().y+5, player.getPosition().z));
			firee.generateParticles(new Vector3f(1000, 8, 250));
			
			ParticleMaster.update(camera);
				
			entity.increaseRotation(0, 1, 0);
			entity2.increaseRotation(0, 1, 0);
			entity3.increaseRotation(0, 1, 0);
			
			renderer.renderShadowMap(entities, sun);
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			//render reflection texture
			
			buffers.bindReflectionFrameBuffer();
			float distance = 2* (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera ,new Vector4f(0, 1, 0 , -water.getHeight()+1));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			//render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera ,new Vector4f(0, -1, 0 , water.getHeight()));
			
			//render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			
			fbo.bindFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,new Vector4f(0, -1, 0 ,100000));
			waterRenderer.render(waters, camera ,sun);
			ParticleMaster.renderParticles(camera);
			fbo.unbindFrameBuffer();
			PostProcessing.doPostProcessing(fbo.getColourTexture());
			
			guiRenderer.render(guis);
			TextMaster.render();
		
			DisplayManager.updateDisplay();	
		}
		
		
		GSocket.socket.disconnect();
		PostProcessing.cleanUp();
		fbo.cleanUp();
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUP();
		guiRenderer.cleanUP();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

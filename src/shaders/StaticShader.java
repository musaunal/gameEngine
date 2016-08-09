package shaders;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_F�LE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_F�LE = "src/shaders/fragmentShader.txt";
	
	
	public StaticShader() {
		super(VERTEX_F�LE, FRAGMENT_F�LE);
	
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	
	
}

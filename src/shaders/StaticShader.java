package shaders;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FÝLE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FÝLE = "src/shaders/fragmentShader.txt";
	
	
	public StaticShader() {
		super(VERTEX_FÝLE, FRAGMENT_FÝLE);
	
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	
	
}

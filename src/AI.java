import java.awt.Graphics2D;

public class AI implements IGameObject {

	private Minimax minimax;
	private Grid grid;
	
	private int timeInterval = 20;
	private int currentTime;
	private boolean startTimer;
	
	public AI(Grid grid) {
		this.grid = grid;
		minimax = new Minimax();
	}
	
	public void makeMove() {
		currentTime = 0;
		startTimer = true;
	}
	
	@Override
	public void update(float deltaTime) {
		if(!startTimer) {
			return;
		}
		
		currentTime += deltaTime;
		if(currentTime >= timeInterval) {
			grid.placeMarker(minimax.getBestMove(grid.getMarkers(), grid.getTurn()));
			startTimer = false;
		}
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		
	}
	
	public boolean isMoving() {
		return startTimer;
	}

}

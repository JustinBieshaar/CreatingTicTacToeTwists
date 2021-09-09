import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class SwipeGame extends Game {

	private GridSwipe grid;
	private GamePanel gamePanel;
	private Screenshaker shaker;
	
	public SwipeGame(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		shaker = new Screenshaker(gamePanel);
		grid = new GridSwipe(shaker);
	}
	
	@Override
	public void update(float deltaTime) {
		shaker.update(deltaTime);
		grid.update(deltaTime);
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		grid.render(graphicsRender);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(grid.isGameEnd()) {
			gamePanel.resetMenu();
		}
		
		grid.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		grid.mouseMoved(e);
	}

}

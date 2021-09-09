import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class ClassicGame extends Game {
	
	private Grid grid;
	private GamePanel gamePanel;
	
	public ClassicGame(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		grid = new Grid();
	}

	@Override
	public void update(float deltaTime) {
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

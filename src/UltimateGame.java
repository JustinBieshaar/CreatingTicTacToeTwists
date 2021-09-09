import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class UltimateGame extends Game {

	private Ultimate ultimate;
	
	public UltimateGame(GamePanel gamePanel) {
		ultimate = new Ultimate(gamePanel);
	}
	
	@Override
	public void update(float deltaTime) {
		ultimate.update(deltaTime);
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		ultimate.render(graphicsRender);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ultimate.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ultimate.mouseMoved(e);
	}

}
